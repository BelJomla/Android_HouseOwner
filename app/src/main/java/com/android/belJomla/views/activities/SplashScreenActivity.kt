package com.android.belJomla.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.android.belJomla.R
import com.android.belJomla.utils.LoggerUtils as l
import com.android.belJomla.viewmodels.MainViewModel


class SplashScreenActivity : AppCompatActivity() {

    val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        viewModel.categories.observe(this, Observer { categories ->
            if (categories == null){
                Handler().postDelayed({
                    goToLoginActivity()
                },3000)
            }
            else
            if (categories.size>0){
                 Handler().postDelayed({
                    goToLoginActivity()
                 },3000)
                viewModel.getProducts()
            }

        })



    }

    private fun goToLoginActivity() {
        l.logMessage(this,"Going to LoginActivity")
        var loginIntent = Intent(this,LoginActivity::class.java)
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(loginIntent)
        viewModel.categories.removeObservers(this)
        finish()

    }

}