package io.github.domi04151309.homeworkapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.github.domi04151309.homeworkapp.DateAdapter.ViewHolder
import io.github.domi04151309.homeworkapp.data.Plan
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
    }

    override fun getItemCount(): Int {
        return fullSize
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTxt: TextView = itemView.findViewById(R.id.dateTxt)
        val listView: ListView = itemView.findViewById(R.id.listView)
    }
}