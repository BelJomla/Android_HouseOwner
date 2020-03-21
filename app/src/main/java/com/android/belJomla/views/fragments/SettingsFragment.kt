package com.android.belJomla.views.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.android.belJomla.R
import com.android.belJomla.viewmodels.SettingsViewModel
import com.android.belJomla.views.activities.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.android.belJomla.utils.Constants as c
import android.os.LocaleList
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.preference.PreferenceManager
import com.android.belJomla.application.BelJomlAApplication
import java.util.*


class SettingsFragment : PreferenceFragmentCompat() {

    private var btnSignOut  : Preference? = null
    private var btnLanguage : ListPreference? = null
    private val sharedPreference: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(
        BelJomlAApplication.getAppContext())
    private val viewModel by activityViewModels<SettingsViewModel>()
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)


         btnSignOut = findPreference<Preference>(getString(R.string.settings_signout))
         btnLanguage = findPreference<ListPreference>(getString(R.string.settings_language))

        setOnClickListeners()



    }

    private fun setOnClickListeners() {
        btnSignOut!!.setOnPreferenceClickListener {
            FirebaseAuth.getInstance().signOut()
            val signOutIntent = Intent(
                context,
                LoginActivity::class.java
            )
            signOutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(signOutIntent)
            requireActivity().finish()
            true
        }
        btnLanguage!!.setOnPreferenceChangeListener{ _, newValue: Any ->
            val currentLocale = sharedPreference.getString(c.SHARED_PREF_LOCALE_KEY,"")?:""
            val selectedLocale = when(newValue as String){
                "arabic"-> "ar"
                "english" -> "en"
                else -> ""
            }
            if (currentLocale!= selectedLocale) {
                sharedPreference.edit().putString(c.SHARED_PREF_LOCALE_KEY,selectedLocale).apply()
                viewModel.updateLocale(selectedLocale)
            }



            true

        }



    }
}