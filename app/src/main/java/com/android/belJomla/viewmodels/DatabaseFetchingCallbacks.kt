package com.android.belJomla.viewmodels

import com.android.belJomla.models.HouseOwnerUser

interface DatabaseFetchingCallbacks {

    fun onUserFetched(houseOwnerUser  :HouseOwnerUser)
}