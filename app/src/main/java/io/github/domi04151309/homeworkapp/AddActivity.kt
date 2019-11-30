package io.github.domi04151309.homeworkapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Theme.set(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        val saveDate = intent.getStringExtra("saveDate") ?: ""
        val displayDate = intent.getStringExtra("displayDate") ?: ""
        val titleTxt = findViewById<TextView>(R.id.titleTxt)
        val titleBox = findViewById<EditText>(R.id.titleBox)

        findViewById<TextView>(R.id.dateTxt).text = resources.getString(R.string.date, displayDate)

        titleBox.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val string = s.toString()
                if (string == "") titleTxt.text = resources.getString(R.string.new_task)
                else titleTxt.text = string
            }
        })

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            finish()
        }
    }
}
