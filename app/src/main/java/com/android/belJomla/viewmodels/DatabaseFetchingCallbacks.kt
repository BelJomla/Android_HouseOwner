package com.android.belJomla.viewmodels

import com.android.belJomla.models.User

interface DatabaseFetchingCallbacks {

    fun onUserFetched(user  :User)
}