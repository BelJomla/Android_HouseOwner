package com.android.BelJomla.models

import com.google.firebase.firestore.Exclude

class User(var firstName : String, var lastName:String,var mobile : String) {

    @Exclude
    var isAuthenticated: Boolean = false
    @Exclude
    var isNew: Boolean = false
    @Exclude
    var isCreated: Boolean = false
    init {
        isAuthenticated = false
        isNew = false
        isCreated = false
    }

    override fun toString(): String {
        return "Name $firstName $lastName\n" +
                "mobile $mobile\n" +
                "isAuth $isAuthenticated"
    }
}