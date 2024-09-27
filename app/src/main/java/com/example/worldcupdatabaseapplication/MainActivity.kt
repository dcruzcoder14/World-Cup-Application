package com.example.worldcupdatabaseapplication
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appDatabase: AppDatabase

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setup the NavController
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Set up the ActionBar with the NavController
        setupActionBarWithNavController(navController)

        // Set up the app database and insert sample data
        appDatabase = AppDatabase.getInstance(this)

        // Add the sample teams to the database
        GlobalScope.launch {
            BootstrapData.insertTeams(appDatabase)
        }

    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onDestroy() {
        Log.d("MainActivity", "onDestroy()")
        GlobalScope.launch {
            appDatabase.teamDao().deleteAllTeams()
        }
        super.onDestroy()
    }

    // Navigate up when the back button is pressed
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
