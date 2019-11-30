package io.github.domi04151309.homeworkapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.github.domi04151309.homeworkapp.DateAdapter.ViewHolder
import java.text.SimpleDateFormat
import java.util.*

class DateAdapter(context: Context, size: Int) : RecyclerView.Adapter<ViewHolder>() {

    private val c: Context = context
    private val fullSize: Int = size
    val halfSize: Int = size / 2
    private var calendar: Calendar = Calendar.getInstance()
    val saveFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val displayFormat: SimpleDateFormat = SimpleDateFormat("d MMM yyyy", Locale.getDefault())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(c).inflate(R.layout.pager_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, position - halfSize)
        holder.dateTxt.text = displayFormat.format(calendar.time)

        val values = c.resources.getStringArray(R.array.planner_empty)
        val adapter = ArrayAdapter<String>(
            c,
            R.layout.list_item,
            R.id.text1,
            values
        )
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