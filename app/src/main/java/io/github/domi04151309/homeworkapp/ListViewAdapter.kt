package io.github.domi04151309.homeworkapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import io.github.domi04151309.homeworkapp.data.Plan
import io.github.domi04151309.homeworkapp.objects.Global
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

internal class ListViewAdapter(private val context: Context,private val date: String, private val itemArray: JSONArray) : BaseAdapter() {

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
                context.startActivity(Intent(context, EditActivity::class.java)
                    .putExtra("date", date)
                    .putExtra("index", position)
                )
            }
            deleteBtn.setOnClickListener {
                AlertDialog.Builder(context)
                    .setTitle(R.string.dialog_delete)
                    .setMessage(R.string.dialog_delete_summary)
                    .setPositiveButton(android.R.string.ok) { _, _ ->
                        plan.deleteTask(date, position)
                        LocalBroadcastManager.getInstance(context).sendBroadcast(Intent(
                            Global.DATA_SET_CHANGED))
                    }
                    .setNegativeButton(android.R.string.cancel) { _, _ -> }
                    .show()
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