package com.android.belJomla.utils


import android.app.Activity
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class FirebaseUtils {

    companion object {
        fun verifyPhoneNumber(activity: Activity, phoneNumber : String, mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks){
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                15,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                activity,               // Activity (for callback binding)
                mCallbacks)
        }

        var isLoggedIn = false
    }
}