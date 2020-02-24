package com.android.belJomla.viewmodels

import com.android.belJomla.models.User

interface VerificationCallbacks {


    fun onSignInCompleteCallback(authenticatedUser : User?)

    fun onUserInFireStoreCreatedCallback()
}