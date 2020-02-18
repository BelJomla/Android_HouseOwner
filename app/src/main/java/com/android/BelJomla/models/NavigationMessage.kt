package com.android.BelJomla.models

class NavigationMessage {
    companion object {
        val EVENT_VERIFICATION_COMPLETE = 2


    }
    var event = -1


    fun VerifactionComplete(){
        event = EVENT_VERIFICATION_COMPLETE
    }





}