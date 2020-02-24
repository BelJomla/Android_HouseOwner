package com.android.belJomla.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.belJomla.models.User
import com.android.belJomla.repositories.AuthenticationRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SettingsViewModel : ViewModel() , VerificationCallbacks{



    val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()
    val repo = AuthenticationRepository(this)

    private val _firstName = MutableLiveData<String>()
    val firstName : LiveData<String>
        get() = _firstName

    private val _lastName = MutableLiveData<String>()
    val lastName : LiveData<String>
        get() = _lastName

    private val _email = MutableLiveData<String>()
    val email : LiveData<String>
        get() = _email

    private val _user = MutableLiveData<User>()
    val user : LiveData<User>
        get() = _user

    private val _isLoading =  MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean>
        get() = _isLoading


    private val _eventUpdateProfile =  MutableLiveData<Boolean>()
    val eventUpdateProfile : LiveData<Boolean>
        get() = _eventUpdateProfile

    init {
        getUser()
        _isLoading.value = false
        _eventUpdateProfile.value = false
        val displayName  = auth.currentUser!!.displayName
        //user.value = User()

    }

    fun getUser(){
        return repo.getUser()
    }
    fun startLoading(){
        _isLoading.value = true
    }
    fun stopLoading(){
        _isLoading.value = false
    }
    fun updateProfile(fname : String , lname : String, mobile : String){
        _eventUpdateProfile.value = true
        startLoading()

        repo.updateUser(fname,lname,mobile)
    }
    fun onUpdateProfileFinished(){
        _eventUpdateProfile.value = false
    }


    override fun onUserFetched(authenticatedUser: User?) {
        stopLoading()
        _user.value = authenticatedUser!!
        onUpdateProfileFinished()
    }

    override fun onUserInFireStoreCreatedCallback() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}