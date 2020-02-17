package com.android.BelJomla.views.models

import com.google.firebase.auth.PhoneAuthProvider

class AuthenticationMessage {

    companion object {
        const val EVENT_CODE_SENT = 1
        const val EVENT_VERIFICATION_FAILED = 2
        const val EVENT_VERIFICATION_COMPLETE = 3
        const val EVENT_RESEND_TOKEN = 4
        const val EVENT_LOGIN_NEXT_CLICKED = 5

    }
     var event =-1
    lateinit var storedVerificationId :String
    lateinit var resendToken : PhoneAuthProvider.ForceResendingToken
    lateinit var phoneNumber : String

    fun setCodeSentEvent(storedVerificationId :String,resendToken : PhoneAuthProvider.ForceResendingToken){
        event = EVENT_CODE_SENT
        this.storedVerificationId = storedVerificationId
        this.resendToken = resendToken

    }
    fun eventLoginNext(phoneNumber : String){
        event = EVENT_LOGIN_NEXT_CLICKED
        this.phoneNumber = phoneNumber
    }
}