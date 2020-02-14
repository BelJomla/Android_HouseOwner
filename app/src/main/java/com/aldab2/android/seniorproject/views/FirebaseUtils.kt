package com.aldab2.android.seniorproject.views


import android.app.Activity
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class FirebaseUtils {

    companion object {
        fun verifyPhoneNumber(activity: Activity, phoneNumber : String, mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks){
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                activity,               // Activity (for callback binding)
                mCallbacks)
        }

        var isLoggedIn = false
    }
}