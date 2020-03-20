package com.android.belJomla.callbacks

import com.android.belJomla.models.HouseOwnerUser
import com.google.firebase.FirebaseException
import java.lang.Exception

interface VerificationCallbacks {


    fun onUserFetched(authenticatedHouseOwnerUser : HouseOwnerUser?)

    fun onUserInFireStoreCreatedCallback()

    fun onVerificationFailed(exception: FirebaseException?)


}