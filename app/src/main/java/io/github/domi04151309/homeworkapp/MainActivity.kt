package io.github.domi04151309.homeworkapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import java.util.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var fab: FloatingActionButton? = null
    private var drawerLayout: DrawerLayout? = null
    private var navView: NavigationView? = null

    private var viewPager: ViewPager2? = null
    private val loadingSize = Integer.MAX_VALUE

    override fun onCreate(savedInstanceState: Bundle?) {
        Theme.setNoActionBar(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab = findViewById(R.id.fab)
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

        fab!!.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE, viewPager!!.currentItem - dateAdapter.halfSize)
            Toast.makeText(this, dateAdapter.saveFormat.format(calendar.time), Toast.LENGTH_LONG).show()
            //startActivity(Intent(this, AddActivity::class.java))
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
                startActivity(Intent(this, Preferences::class.java))
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
    }
}
