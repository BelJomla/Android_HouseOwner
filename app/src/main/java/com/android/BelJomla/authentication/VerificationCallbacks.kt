package com.android.BelJomla.authentication

import com.android.BelJomla.models.User

interface VerificationCallbacks {


    fun onSignInCompleteCallback(authenticatedUser : User?)
}