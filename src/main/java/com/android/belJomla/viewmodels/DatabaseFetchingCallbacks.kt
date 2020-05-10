package com.android.belJomla.viewmodels

import com.android.beljomla.database_classes.HouseOwnerUser

interface DatabaseFetchingCallbacks {

    fun onUserFetched(houseOwnerUser  : HouseOwnerUser)
}