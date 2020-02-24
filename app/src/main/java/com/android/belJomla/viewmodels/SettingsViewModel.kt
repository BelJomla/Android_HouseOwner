package com.android.belJomla.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.belJomla.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SettingsViewModel : ViewModel(){


    val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()

    private val _firstName = MutableLiveData<String>()
    val firstName : LiveData<String>
        get() = _firstName

    private val _lastName = MutableLiveData<String>()
    val lastName : LiveData<String>
        get() = _lastName

    private val _email = MutableLiveData<String>()
    val email : LiveData<String>
        get() = _email

    private val _user = MutableLiveData<String>()
    val user : LiveData<String>
        get() = _user


    init {
        val displayName  = auth.currentUser!!.displayName
        user.value = User()

    }


}