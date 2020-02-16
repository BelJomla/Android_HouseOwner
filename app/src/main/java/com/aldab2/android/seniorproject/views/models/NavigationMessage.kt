package com.aldab2.android.seniorproject.views.models

class NavigationMessage {
    companion object {
        val EVENT_LOGIN_NEXT_CLICKED = 1
        val EVENT_VERIFICATION_COMPLETE = 2


    }
    var event = -1
    fun eventLoginNext(){
        event = EVENT_LOGIN_NEXT_CLICKED
    }

    fun VerifactionComplete(){
        event = EVENT_VERIFICATION_COMPLETE
    }





}