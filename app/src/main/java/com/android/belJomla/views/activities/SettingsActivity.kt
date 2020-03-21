package com.android.belJomla.views.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.android.belJomla.R
import com.android.belJomla.utils.Constants
import com.android.belJomla.utils.LoggerUtils as l
import com.android.belJomla.viewmodels.SettingsViewModel
import java.util.*

class SettingsActivity : LocalizationActivity(), PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        if (getCurrentLanguage().language == "ar") {
            window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL

        }
        if (getCurrentLanguage().language  == "en") {
            window.decorView.layoutDirection = View.LAYOUT_DIRECTION_LTR

        }

        setContentView(R.layout.activity_settings)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = getString(R.string.settings)




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
