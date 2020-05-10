package com.android.belJomla.callbacks

import com.android.beljomla.database_classes.HouseOwnerUser
import com.google.firebase.FirebaseException

interface VerificationCallbacks {


    fun onUserFetched(authenticatedHouseOwnerUser : HouseOwnerUser?)

    fun onUserInFireStoreCreatedCallback()

    fun onVerificationFailed(exception: FirebaseException?)



}