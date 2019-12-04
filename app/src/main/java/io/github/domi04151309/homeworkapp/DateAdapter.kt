package io.github.domi04151309.homeworkapp

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import io.github.domi04151309.homeworkapp.DateAdapter.ViewHolder
import io.github.domi04151309.homeworkapp.data.Plan
import io.github.domi04151309.homeworkapp.objects.Global
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.*

class DateAdapter(context: Context, size: Int) : RecyclerView.Adapter<ViewHolder>() {

    private val c: Context = context
    private val fullSize: Int = size
    val halfSize: Int = size / 2
    private var calendar: Calendar = Calendar.getInstance()
    val saveFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    val displayFormat: SimpleDateFormat = SimpleDateFormat("d MMM yyyy", Locale.getDefault())
    private val plan = Plan(c)
    private var date: String = ""
    private var array: JSONArray = JSONArray()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(c).inflate(R.layout.pager_item, parent, false)
        return ViewHolder(view)
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

        val cal = Calendar.getInstance()
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            cal.set(Calendar.HOUR_OF_DAY, 0)
            cal.set(Calendar.MINUTE, 0)
            cal.set(Calendar.SECOND, 0)
            cal.set(Calendar.MILLISECOND, 0)

            LocalBroadcastManager.getInstance(c).sendBroadcast(
                Intent(Global.LOAD_REQUESTED)
                    .putExtra("difference", getDaysDifference(cal.time))
            )
        }
        holder.jumpBtn.setOnClickListener {
            DatePickerDialog(c, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    override fun getItemCount(): Int {
        return fullSize
    }

    private fun getDaysDifference(to: Date): Int {
        val today = Calendar.getInstance()
        today.set(Calendar.HOUR_OF_DAY, 0)
        today.set(Calendar.MINUTE, 0)
        today.set(Calendar.SECOND, 0)
        today.set(Calendar.MILLISECOND, 0)
        return ((to.time - today.time.time) / (1000 * 60 * 60 * 24)).toInt()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTxt: TextView = itemView.findViewById(R.id.dateTxt)
        val jumpBtn: ImageButton = itemView.findViewById(R.id.jumpBtn)
        val listView: ListView = itemView.findViewById(R.id.listView)
    }
}