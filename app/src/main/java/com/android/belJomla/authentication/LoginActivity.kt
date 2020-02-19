package com.android.belJomla.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import com.android.belJomla.R


import com.android.belJomla.utils.Constants
import com.android.belJomla.views.activities.MainActivity
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {
    companion object {
    private val TAG = LoginActivity::class.java.simpleName
    }
    private val fm = supportFragmentManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val viewModel by viewModels<AuthenticationViewModel>()
        FirebaseAuth.getInstance().signOut()

        if (savedInstanceState == null) {


            if (FirebaseAuth.getInstance().currentUser != null){
                goToMainActivity()
            }

            fm.beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.slide_out_right)
                .add(R.id.login_fragment_container, LoginFragment.getInstance(), Constants.LOGIN_FRAGMENT_TAG).commit()

        }

        viewModel.eventCodeSent.observe(this, Observer { hasSentCode ->
            if (hasSentCode) {
                fm.popBackStack(Constants.VERIFICATION_FRAGMENT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                fm.beginTransaction().replace(
                    R.id.login_fragment_container,
                    VerificationFragment()
                ).addToBackStack(Constants.VERIFICATION_FRAGMENT_TAG)
                    .commit()
                viewModel.onCodeSentComplete()
            }
        })



        viewModel.eventVerificationFailed.observe(this, Observer { hasFailed ->
            if (hasFailed) {
                Toast.makeText(this, "Verification Failed", Toast.LENGTH_SHORT).show()
                viewModel.onVerificationComplete()
            }
        })
        viewModel.eventVerificationSuccess.observe(this, Observer { hasSucceeded ->
            if (hasSucceeded) {
                Toast.makeText(this, "Verification Success", Toast.LENGTH_SHORT).show()
                viewModel.onVerificationComplete()
            }
        })
        viewModel.user.observe(this, Observer { user ->
            if (user.isAuthenticated) {
                goToMainActivity()

            } else {
                Toast.makeText(this, "User not Authenticated", Toast.LENGTH_SHORT).show()

            }
        })


    }

    private fun goToMainActivity() {
        val mainActivityIntent = Intent(this, MainActivity::class.java)
        mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(mainActivityIntent)
        finish()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()


    }
}
