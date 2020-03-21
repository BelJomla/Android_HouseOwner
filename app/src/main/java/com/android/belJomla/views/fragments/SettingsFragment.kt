package com.android.belJomla.views.fragments

import android.content.Intent
import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.android.belJomla.R
import com.android.belJomla.views.activities.LoginActivity
import com.android.belJomla.views.activities.SettingsActivity
import com.google.firebase.auth.FirebaseAuth
import java.util.*

import com.android.belJomla.utils.LoggerUtils as l


class SettingsFragment : PreferenceFragmentCompat() {

    private var btnSignOut  : Preference? = null
    private var btnLanguage : ListPreference? = null
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
            signOutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(signOutIntent)
            (requireActivity() as SettingsActivity).finish()
            true
        }
        l.logMessage(this,"From Fragment :  ${(activity as SettingsActivity).getCurrentLanguage().language}")
        btnLanguage!!.setOnPreferenceChangeListener{ _, newValue: Any ->
            val currentLanguage = (activity as SettingsActivity).getCurrentLanguage().isO3Language
            val selectedLanguage = when(newValue as String){
                "arabic"-> "ar"
                "english" -> "en"
                else -> ""
            }
            if (currentLanguage!= selectedLanguage) {
                (activity as SettingsActivity).setLanguage(Locale(selectedLanguage))
            }



            true

        }



    }
}