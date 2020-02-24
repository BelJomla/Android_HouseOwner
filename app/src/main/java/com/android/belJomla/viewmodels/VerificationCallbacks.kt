package com.android.belJomla.viewmodels

import com.android.belJomla.models.User

interface VerificationCallbacks {


    fun onUserFetched(authenticatedUser : User?)

    fun onUserInFireStoreCreatedCallback()


}