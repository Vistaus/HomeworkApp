package io.github.domi04151309.homeworkapp.receivers

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.service.quicksettings.TileService
import io.github.domi04151309.homeworkapp.services.AddTaskQS

class OnBootCompleted : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if(intent.action == Intent.ACTION_BOOT_COMPLETED && Build.VERSION.SDK_INT >= 24) {
            TileService.requestListeningState(context, ComponentName(context , AddTaskQS::class.java))
        }
    }
}