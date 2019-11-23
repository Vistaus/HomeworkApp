package io.github.domi04151309.homeworkapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var fab: FloatingActionButton? = null
    private var drawerLayout: DrawerLayout? = null
    private var navView: NavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab = findViewById(R.id.fab)
        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        fab!!.setOnClickListener {
            //startActivity(Intent(this, DevicesActivity::class.java))
        }

        findViewById<ImageView>(R.id.menu_icon).setOnClickListener {
            drawerLayout!!.openDrawer(GravityCompat.START)
        }

        navView!!.setNavigationItemSelectedListener(this)
        navView!!.setCheckedItem(R.id.nav_planner)
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
            }
            R.id.nav_settings -> {
            }
            R.id.nav_source -> {
            }
        }
        drawerLayout!!.closeDrawer(GravityCompat.START)
        return true
    }
}
