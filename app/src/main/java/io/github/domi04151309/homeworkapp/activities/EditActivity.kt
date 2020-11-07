package io.github.domi04151309.homeworkapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.github.domi04151309.homeworkapp.R
import io.github.domi04151309.homeworkapp.data.Plan
import io.github.domi04151309.homeworkapp.data.PlanItem
import io.github.domi04151309.homeworkapp.helpers.Global
import io.github.domi04151309.homeworkapp.helpers.Theme
import org.json.JSONObject

class EditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Theme.set(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val date = intent.getStringExtra("date") ?: ""
        val index = intent.getIntExtra("index", -1)
        val titleTxt = findViewById<TextView>(R.id.titleTxt)
        val titleBox = findViewById<EditText>(R.id.titleBox)
        val descriptionBox = findViewById<EditText>(R.id.descriptionBox)
        val plan = Plan(this)
        val item = plan.convertToPlanItem(plan.getDay(date).get(index) as JSONObject)

        titleTxt.text = item.title
        titleBox.setText(item.title)
        descriptionBox.setText(item.description)

        titleBox.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val string = s.toString()
                if (string == "") titleTxt.text = resources.getString(R.string.edit_task)
                else titleTxt.text = string
            }
        })

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            val title = titleBox.text.toString()
            if (title == "") {
                AlertDialog.Builder(this)
                    .setTitle(R.string.err_missing_title)
                    .setMessage(R.string.err_missing_title_summary)
                    .setPositiveButton(android.R.string.ok) { _, _ -> }
                    .show()
                return@setOnClickListener
            }
            val newItem = PlanItem(title)
            newItem.description = descriptionBox.text.toString()
            plan.deleteTask(date, index)
            plan.addTask(date, newItem)
            LocalBroadcastManager.getInstance(this)
                .sendBroadcast(Intent(Global.DATA_SET_CHANGED))
            finish()
        }
    }
}
