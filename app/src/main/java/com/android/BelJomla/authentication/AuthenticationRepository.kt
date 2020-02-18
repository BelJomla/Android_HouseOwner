package com.android.BelJomla.authentication

import android.util.Log
import com.android.BelJomla.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential

 class AuthenticationRepository(var verifCallbacks: VerificationCallbacks) {



    val TAG = AuthenticationRepository::class.java.simpleName

    private val auth = FirebaseAuth.getInstance()

    fun funa(){}
    fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential)  {

        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val isNewUser = task.result!!.additionalUserInfo!!.isNewUser
                    val firebaseUser = auth.currentUser
                    Log.d(TAG, "firebase user : $firebaseUser isNewUser : $isNewUser")

                    if (firebaseUser != null ) {
                        val uid = firebaseUser.uid
                        val name = firebaseUser.displayName
                        val email = firebaseUser.email
                        val mobile = firebaseUser.phoneNumber
                        val user = User(name ?: "", name ?: "", mobile!!)
                        user.isNew = isNewUser
                        user.isAuthenticated = true
                        Log.d(TAG, "onSignInCompleteCallback  User : $user\n" +
                                "FirebaseUser ${auth.currentUser?.phoneNumber}")
                        verifCallbacks.onSignInCompleteCallback(user)

                    }
                    else {
                        val user = User("","", "")
                        user.isNew = isNewUser
                        user.isAuthenticated = true
                        Log.d(TAG, "onSignInCompleteCallback  User : $user\n" +
                                "FirebaseUser ${auth.currentUser?.phoneNumber}")
                        verifCallbacks.onSignInCompleteCallback(user)
                    }


                    // ...
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        val user = User("","","")
                        user.isAuthenticated = false
                        user.isCreated = false
                        Log.d(TAG, "onSignInCompleteCallback  User : $user\n" +
                                "FirebaseUser ${auth.currentUser?.phoneNumber}")
                        verifCallbacks.onSignInCompleteCallback(user)


                    }
                }

            }
        /*
        Wait until the authentication thread is over
         */



    }

}