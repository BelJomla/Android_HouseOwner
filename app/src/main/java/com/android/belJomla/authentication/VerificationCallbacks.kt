package com.android.belJomla.authentication

import com.android.belJomla.models.User

interface VerificationCallbacks {


    fun onSignInCompleteCallback(authenticatedUser : User?)

    fun onUserInFireStoreCreatedCallback()
}