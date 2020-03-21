package com.android.belJomla.views.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.LocaleList
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.NavUtils
import androidx.lifecycle.Observer
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.android.belJomla.R
import com.android.belJomla.application.BelJomlAApplication
import com.android.belJomla.utils.Constants
import com.android.belJomla.utils.LoggerUtils as l
import com.android.belJomla.viewmodels.SettingsViewModel
import com.android.belJomla.views.fragments.SettingsFragment
import java.util.*

class SettingsActivity : AppCompatActivity(), PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {
    val viewmodel by viewModels<SettingsViewModel>()
    var newLocale : String = ""
    val preferences : SharedPreferences? = PreferenceManager.getDefaultSharedPreferences(
        BelJomlAApplication.getAppContext())
    val language = preferences!!.getString(Constants.SHARED_PREF_LOCALE_KEY,BelJomlAApplication.getAppContext()!!.getString(R.string.locale))!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        setContentView(R.layout.activity_settings)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = getString(R.string.settings)

        viewmodel.currentLocale.observe(this, Observer {
            l.logMessage(this,"Locale Changed To $it")
            if (newLocale != it) {
                l.logMessage(this,"newLocale != it : ${newLocale != it}")
                //setLocale(it)

                val refresh = Intent(this, SplashScreenActivity::class.java)
                refresh.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(refresh)
                finish() // if the activity running has it's own context
            }


        })


    }
    override fun attachBaseContext(newBase: Context?) {
        val newLocale = Locale(language)

        super.attachBaseContext(ContextWrapper.wrap(newBase!!,newLocale)
        )
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onPreferenceStartFragment(caller: PreferenceFragmentCompat, pref: Preference): Boolean {
        // Instantiate the new Fragment
        val args = pref.extras
        val fragment = supportFragmentManager.fragmentFactory.instantiate(
            classLoader,
            pref.fragment)
        fragment.arguments = args
        fragment.setTargetFragment(caller, 0)
        // Replace the existing Fragment with the new Fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.settings_fragment_container, fragment)
            .addToBackStack(null)
            .commit()
        return true
    }
}
