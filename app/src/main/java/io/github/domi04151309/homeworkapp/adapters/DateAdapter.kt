package io.github.domi04151309.homeworkapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.datepicker.MaterialDatePicker
import io.github.domi04151309.homeworkapp.R
import io.github.domi04151309.homeworkapp.adapters.DateAdapter.ViewHolder
import io.github.domi04151309.homeworkapp.data.Plan
import io.github.domi04151309.homeworkapp.helpers.Global
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.*

class DateAdapter(
    private val c: Context,
    private val size: Int
) : RecyclerView.Adapter<ViewHolder>() {

    val halfSize: Int = size / 2
    private var calendar: Calendar = Calendar.getInstance()
    val saveFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    val displayFormat: SimpleDateFormat = SimpleDateFormat("d MMM yyyy", Locale.getDefault())
    private val plan = Plan(c)
    private var date: String = ""
    private var array: JSONArray = JSONArray()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(c).inflate(R.layout.pager_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, position - halfSize)
        holder.dateTxt.text = displayFormat.format(calendar.time)

        date = saveFormat.format(calendar.time)
        array = plan.getDay(date)
        if (array.length() == 0) array.put(c.resources.getString(R.string.planner_empty))

        val adapter = ListViewAdapter(c, date, array)
        holder.listView.adapter = adapter

        holder.jumpBtn.setOnClickListener {
            val dialog = MaterialDatePicker
                .Builder
                .datePicker()
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()

            dialog.addOnPositiveButtonClickListener {
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = it
                LocalBroadcastManager.getInstance(c).sendBroadcast(
                    Intent(Global.LOAD_REQUESTED)
                        .putExtra("difference", getDaysDifference(calendar.time))
                )
            }

            dialog.show((c as AppCompatActivity).supportFragmentManager, dialog.toString())
        }
    }

    override fun getItemCount(): Int {
        return size
    }

    private fun getDaysDifference(to: Date): Int {
        val today = Calendar.getInstance()
        today.set(Calendar.HOUR_OF_DAY, 0)
        today.set(Calendar.MINUTE, 0)
        today.set(Calendar.SECOND, 0)
        today.set(Calendar.MILLISECOND, 0)
        return ((to.time - today.time.time) / (1000 * 60 * 60 * 24)).toInt()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTxt: TextView = itemView.findViewById(R.id.dateTxt)
        val jumpBtn: ImageButton = itemView.findViewById(R.id.jumpBtn)
        val listView: ListView = itemView.findViewById(R.id.listView)
    }
}