package com.aldab2.android.seniorproject.views.models

import com.google.firebase.auth.PhoneAuthProvider

class AuthenticationMessage {

    companion object {
        val Event_Code_Sent = 1
        val Event_Verification_Failed = 2
        val Event_Verification_Complete = 3
        val Event_Resend_Token = 4

    }
     var event =-1
    lateinit var storedVerificationId :String
    lateinit var resendToken : PhoneAuthProvider.ForceResendingToken

    fun setCodeSentEvent(storedVerificationId :String,resendToken : PhoneAuthProvider.ForceResendingToken){
        event = Event_Code_Sent
        this.storedVerificationId = storedVerificationId
        this.resendToken = resendToken

    }
}