package com.android.belJomla.views.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import com.android.belJomla.R
import com.android.belJomla.views.fragments.LoginFragment
import com.android.belJomla.views.fragments.SignUpFragment
import com.android.belJomla.views.fragments.VerificationFragment
import com.android.belJomla.models.HouseOwnerUser


import com.android.belJomla.utils.Constants
import com.android.belJomla.viewmodels.AuthenticationViewModel
import com.android.belJomla.views.fragments.SettingsFragment
import com.google.firebase.auth.FirebaseAuth
import java.util.*


class LoginActivity : AppCompatActivity() {
    companion object {
    private val TAG = LoginActivity::class.java.simpleName
    }
    private val fm = supportFragmentManager
    private var mLocale : Locale? = null

   // private val preferences: SharedPreferences = getSharedPreferences(Constants.MAIN_PREF_NAME, Context.MODE_PRIVATE)
    var language = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)





     /*   if (language == "ar") {
            window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL

        }
        if (language == "en") {
            window.decorView.layoutDirection = View.LAYOUT_DIRECTION_LTR

        }*/

        setContentView(R.layout.activity_login)
        val viewModel by viewModels<AuthenticationViewModel>()
        //FirebaseAuth.getInstance().signOut()

        if (savedInstanceState == null) {


            if (FirebaseAuth.getInstance().currentUser != null){
                goToMainActivity()
            }

            fm.beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.slide_out_right)
                .add(R.id.login_fragment_container,
                    LoginFragment.newInstance(), Constants.LOGIN_FRAGMENT_TAG).commit()

        }

        initObservers(viewModel)


    }

    private fun initObservers(viewModel: AuthenticationViewModel) {
        viewModel.eventCodeSent.observe(this, Observer { hasSentCode ->
            if (hasSentCode) {
                fm.popBackStack(
                    Constants.VERIFICATION_FRAGMENT_TAG,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
                fm.beginTransaction().replace(
                    R.id.login_fragment_container,
                    VerificationFragment.newInstance()
                ).addToBackStack(Constants.VERIFICATION_FRAGMENT_TAG)
                    .commit()
                viewModel.onCodeSentComplete()
            }
        })




        viewModel.houseOwnerUser.observe(this, Observer { user ->
            if (user.isAuthenticated && !user.isNew) {
                goToMainActivity()

            } else if (user.isAuthenticated && user.isNew) {
                fm.beginTransaction().replace(
                    R.id.login_fragment_container,
                    SignUpFragment.newInstance()
                ).addToBackStack(Constants.SIGNUP_FRAGMENT_TAG)
                    .commit()
                //Toast.makeText(this, "New HouseOwnerUser", Toast.LENGTH_SHORT).show()

            }
            /* else {
                //Toast.makeText(this, "HouseOwnerUser not Authenticated", Toast.LENGTH_SHORT).show()

            }*/
        })

       /* viewModel.eventFirestoreUserCreated.observe(this, Observer { isCreatingUserInProgress ->
            // If the app is not in the signup screen !isCreatingUserInProgress and the houseOwnerUser is authenticated and is new
            if (!isCreatingUserInProgress && viewModel.houseOwnerUser.value != null) {
                val houseOwnerUser: HouseOwnerUser = viewModel.houseOwnerUser.value!!
                if (houseOwnerUser.isAuthenticated) {
                    goToMainActivity()
                }
            }
        })*/
        viewModel.eventFirestoreUserCreated.observe(this, Observer {eventUserCreated ->
            if (eventUserCreated){
                goToMainActivity()
                viewModel.onEventUserInFireStoreCreatedHandled()
            }
        })
    }


    private fun goToMainActivity() {
        val mainActivityIntent = Intent(this, MainActivity::class.java)
        mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(mainActivityIntent)
        finish()
    }



}
