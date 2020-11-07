package io.github.domi04151309.homeworkapp.data

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import io.github.domi04151309.homeworkapp.helpers.Global
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class Plan(context: Context) {

    private val _prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    private fun getPlannerObject(): JSONObject {
        return JSONObject(_prefs.getString("planner_json", Global.DEFAULT_JSON) ?: Global.DEFAULT_JSON)
    }

    fun convertToPlanItem(jsonObj: JSONObject): PlanItem {
        val planItem = PlanItem(jsonObj.getString("title"))
        planItem.description = jsonObj.getString("description")
        planItem.done = jsonObj.getBoolean("done")
        return planItem
    }

    fun getDay(date: String): JSONArray {
        return try {
            getPlannerObject().getJSONArray(date)
        } catch (e: Exception) {
            JSONArray()
        }
    }

    fun addTask(date: String, planItem: PlanItem) {
        val planObject = JSONObject()
            .put("title", planItem.title)
            .put("description", planItem.description)
            .put("done", planItem.done)
        val newDateArray = getDay(date).put(planObject)
        _prefs.edit().putString("planner_json", getPlannerObject().put(date, newDateArray).toString()).apply()
    }

    fun deleteTask(date: String, index: Int) {
        val newDateArray = getDay(date)
        newDateArray.remove(index)
        _prefs.edit().putString("planner_json", getPlannerObject().put(date, newDateArray).toString()).apply()
    }

    fun done(done: Boolean, date: String, index: Int) {
        val newDateArray = getDay(date)
        newDateArray.getJSONObject(index).put("done", done)
        _prefs.edit().putString("planner_json", getPlannerObject().put(date, newDateArray).toString()).apply()
    }
}