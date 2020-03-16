package com.android.belJomla.callbacks

import com.android.belJomla.models.HouseOwnerUser

interface VerificationCallbacks {


    fun onUserFetched(authenticatedHouseOwnerUser : HouseOwnerUser?)

    fun onUserInFireStoreCreatedCallback()


}