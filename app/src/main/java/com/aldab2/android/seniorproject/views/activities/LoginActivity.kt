package com.aldab2.android.seniorproject.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.aldab2.android.seniorproject.R
import com.aldab2.android.seniorproject.views.fragments.LoginFragment
import com.aldab2.android.seniorproject.views.fragments.VerificationFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

import com.aldab2.android.seniorproject.views.models.NavigationMessage
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider


class LoginActivity : AppCompatActivity() {
    private val TAG = localClassName
    private val fm = supportFragmentManager
    lateinit var  callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    lateinit var storedVerificationId :String
    lateinit var resendToken :PhoneAuthProvider.ForceResendingToken

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (savedInstanceState == null) {

            fm.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .add(R.id.login_fragment_container, LoginFragment()).commit()

        }

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:$credential")
                // TODO Implement the signInWithPhoneAuthCredential(credential) method

                //signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e)

                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    Toast.makeText(this@LoginActivity,getString(R.string.invalid_code),Toast.LENGTH_SHORT).show()
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    Toast.makeText(this@LoginActivity,getString(R.string.too_many_requests),Toast.LENGTH_SHORT).show()

                }

                // Show a message and update the UI
                // ...
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:$verificationId")

                // Save verification ID and resending token so we can use them later
                storedVerificationId = verificationId
                resendToken = token

                //TODO Implement the code sent events

                // ...
            }
        }

    }

    @Subscribe
    fun onMessageEvent(navigation: NavigationMessage) {/* Do something */
        if (navigation.event == NavigationMessage.EVENT_LOGIN_NEXT_CLICKED) {
            fm.beginTransaction().replace(R.id.login_fragment_container, VerificationFragment()).addToBackStack(null)
                .commit()

            Toast.makeText(this, "Button Clicked", Toast.LENGTH_SHORT).show()
        }
        if (navigation.event == NavigationMessage.EVENT_VERIFICATION_COMPLETE) {
             val mainPageIntent = Intent(this, MainActivity::class.java)
                           mainPageIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                           startActivity(mainPageIntent)
                           this!!.finish()
        }

        }


    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)


    }
}
