package com.app.cocktailmachine.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.app.cocktailmachine.Constants
import com.app.cocktailmachine.R
import com.app.cocktailmachine.connections.MqttConnection
import com.app.cocktailmachine.model.Cocktail
import com.app.cocktailmachine.model.Grocery
import com.app.cocktailmachine.model.SharedPreferenceHelper
import com.app.cocktailmachine.ui.main.HomeFragment
import com.app.cocktailmachine.util.Utils
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var cocktails: List<Cocktail>
    lateinit var mqttConnection: MqttConnection
    lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        val sharedPreferences = SharedPreferenceHelper.getInstance(this)
        sharedPreferences.updateGrocerySpots()


        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_all_cocktails,
                R.id.nav_categories,
                R.id.nav_search,
                R.id.nav_test,
                R.id.nav_stats,
                R.id.nav_settings
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        cocktails = Utils.loadCocktailData(applicationContext).sortedBy { x -> x.name }


        Constants.bluetoothConnection.init(applicationContext)
        try {
            Constants.mqttConnection.init(applicationContext)
        }
        catch (e: Exception) {
            Log.d("Error", Log.getStackTraceString(e))
        }



    }


/*
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_settings) {
            startActivity(Intent(applicationContext, SettingsActivity::class.java))
        } else {
            return super.onOptionsItemSelected(item)
        }
        return true
    }

 */

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        val drawer: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            val navController = findNavController(R.id.nav_host_fragment)
            if (navController.currentDestination!!.id != R.id.nav_home) {
                navController.navigate(R.id.nav_home)
            } else {
                Constants.bluetoothConnection.closeConnection(applicationContext)
                finish()
                //super.onBackPressed()
            }
        }
    }
}

