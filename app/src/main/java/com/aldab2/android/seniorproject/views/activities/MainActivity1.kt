package com.aldab2.android.seniorproject.views.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.onNavDestinationSelected
import com.aldab2.android.seniorproject.R
import com.google.android.material.appbar.AppBarLayout


class MainActivity1 : AppCompatActivity() {

    companion object {
        val TAG = MainActivity1.javaClass.simpleName

    }
    private lateinit var  navController: NavController
    private lateinit var mActionBar : AppBarLayout
    private lateinit var navView: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main1)


        setSupportActionBar(findViewById(R.id.toolbar))
        navController = findNavController(R.id.nav_host_fragment)
         navView = findViewById(R.id.nav_view)
         mActionBar = findViewById<AppBarLayout>(R.id.appbar)

        NavigationUI.setupActionBarWithNavController(this,navController)
        NavigationUI.setupWithNavController(navView,navController)


        //navView.setupWithNavController(navController)

        navView.setOnNavigationItemSelectedListener {item ->

            onNavDestinationSelected(item, navController)
     }
    }
    override fun onSupportNavigateUp(): Boolean {
        navController.navigateUp()
        return super.onSupportNavigateUp()
    }
}
