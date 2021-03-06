package com.android.belJomla.views.activities

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.onNavDestinationSelected
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.android.belJomla.R
import com.android.belJomla.views.fragments.ThankYouFragment
import com.google.android.material.appbar.AppBarLayout
import com.android.belJomla.utils.Constants as c
import java.util.*


class MainActivity : LocalizationActivity(), ThankYouFragment.LifeCycleListener {

    companion object {
        val TAG = MainActivity.javaClass.simpleName

    }
    private lateinit var  navController: NavController
    private lateinit var mActionBar : AppBarLayout
    private lateinit var navView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        if (getCurrentLanguage().language == "ar") {
            window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL

        }
        if (getCurrentLanguage().language  == "en") {
            window.decorView.layoutDirection = View.LAYOUT_DIRECTION_LTR

        }

        setContentView(R.layout.activity_main)








        setSupportActionBar(findViewById(R.id.toolbar))
        navController = findNavController(R.id.nav_host_fragment)
         navView = findViewById(R.id.nav_view)

        NavigationUI.setupWithNavController(navView,navController)



        navView.setOnNavigationItemSelectedListener {item ->

            onNavDestinationSelected(item, navController)
     }
    }







    override fun onSupportNavigateUp(): Boolean {
        navController.navigateUp()
        return super.onSupportNavigateUp()
    }

    override fun onThankyouFragmentResumed() {
        navView.visibility = View.GONE
    }

    override fun onThankyouFragmentPaused() {
        navView.visibility = View.VISIBLE
    }

}
