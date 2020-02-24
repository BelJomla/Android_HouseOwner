package com.android.belJomla.models

import com.google.firebase.firestore.Exclude

class User(var mobile : String) {

    @get:Exclude
    var isAuthenticated: Boolean = false
    @get:Exclude
    var isNew: Boolean = false
    @get:Exclude
    var isCreated: Boolean = false
    var email : String = ""
    var firstName : String = ""
    var lastName :String = ""
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