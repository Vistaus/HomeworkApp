package io.github.domi04151309.homeworkapp.activities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import io.github.domi04151309.homeworkapp.R
import io.github.domi04151309.homeworkapp.adapters.DateAdapter
import io.github.domi04151309.homeworkapp.helpers.Global
import io.github.domi04151309.homeworkapp.helpers.Theme
import java.util.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var drawerLayout: DrawerLayout? = null
    private var navView: NavigationView? = null

    private var viewPager: ViewPager2? = null
    private val loadingSize = Integer.MAX_VALUE
    private var reload = false

    private val dataSetReceiver = object : BroadcastReceiver() {
        override fun onReceive(c: Context, intent: Intent) {
            viewPager!!.adapter!!.notifyDataSetChanged()
        }
    }

    private val loadRequestedReceiver = object : BroadcastReceiver() {
        override fun onReceive(c: Context, intent: Intent) {
            viewPager!!.currentItem = loadingSize / 2 + intent.getIntExtra("difference", 0)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Theme.setNoActionBar(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        findViewById<ImageView>(R.id.menu_icon).setOnClickListener {
            drawerLayout!!.openDrawer(GravityCompat.START)
        }

        navView!!.setNavigationItemSelectedListener(this)
        navView!!.setCheckedItem(R.id.nav_planner)

        viewPager = findViewById(R.id.pager)
        val dateAdapter = DateAdapter(this, loadingSize)

        viewPager!!.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager!!.adapter = dateAdapter
        viewPager!!.currentItem = loadingSize / 2

        val localBroadcastManager = LocalBroadcastManager.getInstance(this)
        localBroadcastManager.registerReceiver(dataSetReceiver, IntentFilter(Global.DATA_SET_CHANGED))
        localBroadcastManager.registerReceiver(loadRequestedReceiver, IntentFilter(Global.LOAD_REQUESTED))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE, viewPager!!.currentItem - dateAdapter.halfSize)

            startActivity(Intent(this, AddActivity::class.java)
                .putExtra("saveDate", dateAdapter.saveFormat.format(calendar.time))
                .putExtra("displayDate", dateAdapter.displayFormat.format(calendar.time))
            )
        }
    }

    override fun onBackPressed() {
        if (drawerLayout!!.isDrawerOpen(GravityCompat.START))
            drawerLayout!!.closeDrawer(GravityCompat.START)
        else
            super.onBackPressed()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_planner -> {
                viewPager!!.currentItem = loadingSize / 2
            }
            R.id.nav_settings -> {
                reload = true
                startActivity(Intent(this, SettingsActivity::class.java))
            }
            R.id.nav_source -> {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Domi04151309/HomeworkApp")))
            }
        }
        drawerLayout!!.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onResume() {
        super.onResume()
        navView!!.setCheckedItem(R.id.nav_planner)
        if (reload) {
            viewPager!!.adapter!!.notifyDataSetChanged()
            reload = false
        }
    }
}
