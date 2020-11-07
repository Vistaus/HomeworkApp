package io.github.domi04151309.homeworkapp.services

import android.annotation.TargetApi
import android.content.Intent
import android.os.Build
import android.service.quicksettings.TileService
import io.github.domi04151309.homeworkapp.activities.AddActivity
import java.text.SimpleDateFormat
import java.util.*

@TargetApi(Build.VERSION_CODES.N)
class AddTaskQS : TileService() {

    override fun onClick() {
        unlockAndRun {
            val calendar: Calendar = Calendar.getInstance()
            startActivityAndCollapse(Intent(this, AddActivity::class.java)
                .putExtra("saveDate", SimpleDateFormat("yyyy-MM-dd", Locale.US).format(calendar.time))
                .putExtra("displayDate", SimpleDateFormat("d MMM yyyy", Locale.getDefault()).format(calendar.time))
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }
    }
}