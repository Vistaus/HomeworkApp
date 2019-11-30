package io.github.domi04151309.homeworkapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import io.github.domi04151309.homeworkapp.data.Plan
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

internal class ListViewAdapter(private val context: Context, private val itemArray: JSONArray) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private val plan = Plan(context)

    override fun getCount(): Int {
        return itemArray.length()
    }

    override fun getItem(position: Int): Any {
        return itemArray[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val vi: View = convertView ?: inflater.inflate(R.layout.list_item, parent, false)

        val titleTxt = vi.findViewById<TextView>(R.id.title)
        val descriptionTxt = vi.findViewById<TextView>(R.id.description)
        val doneBtn = vi.findViewById<ImageButton>(R.id.doneBtn)
        val editBtn = vi.findViewById<ImageButton>(R.id.editBtn)
        val deleteBtn = vi.findViewById<ImageButton>(R.id.deleteBtn)

        try {
            val item = plan.convertToPlanItem(itemArray[position] as JSONObject)
            titleTxt.text = item.title
            if (item.description == "") descriptionTxt.text = context.resources.getString(R.string.planner_no_description)
            else descriptionTxt.text = item.description

            doneBtn.setOnClickListener {
                Toast.makeText(context, R.string.btn_done, Toast.LENGTH_SHORT).show()
            }
            editBtn.setOnClickListener {
                Toast.makeText(context, R.string.btn_edit, Toast.LENGTH_SHORT).show()
            }
            deleteBtn.setOnClickListener {
                Toast.makeText(context, R.string.btn_delete, Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            titleTxt.text = itemArray[position].toString()
            doneBtn.visibility = View.GONE
            editBtn.visibility = View.GONE
            deleteBtn.visibility = View.GONE
        }
        return vi
    }
}