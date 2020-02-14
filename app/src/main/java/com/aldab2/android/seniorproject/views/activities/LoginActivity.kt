package com.aldab2.android.seniorproject.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aldab2.android.seniorproject.R
import com.aldab2.android.seniorproject.views.fragments.LoginFragment

class LoginActivity : AppCompatActivity() {

    private val fm = supportFragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (savedInstanceState == null) {

            fm.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .add(R.id.login_fragment_container, LoginFragment()).commit()

        }

    }
}
