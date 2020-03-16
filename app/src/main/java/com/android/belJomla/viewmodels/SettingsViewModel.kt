package com.android.belJomla.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.belJomla.callbacks.VerificationCallbacks
import com.android.belJomla.models.HouseOwnerUser
import com.android.belJomla.repositories.UserRepository

class SettingsViewModel : ViewModel() , VerificationCallbacks {
    private val TAG = javaClass.simpleName



    private val repo = UserRepository(this)

    private val _firstName = MutableLiveData<String>()
    val firstName : LiveData<String>
        get() = _firstName

    private val _lastName = MutableLiveData<String>()
    val lastName : LiveData<String>
        get() = _lastName

    private val _email = MutableLiveData<String>()
    val email : LiveData<String>
        get() = _email

    private val _user = MutableLiveData<HouseOwnerUser>()
    val houseOwnerUser : LiveData<HouseOwnerUser>
        get() = _user

    private val _isLoading =  MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean>
        get() = _isLoading


    private val _eventUpdateProfile =  MutableLiveData<Boolean>()
    val eventUpdateProfile : LiveData<Boolean>
        get() = _eventUpdateProfile

    init {
        //getHouseOwnerUser()
        _isLoading.value = false
        _eventUpdateProfile.value = false
        _user.value = repo.currentHouseOwnerUser


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


    override fun onUserFetched(authenticatedHouseOwnerUser: HouseOwnerUser?) {
        stopLoading()
        _user.value = authenticatedHouseOwnerUser!!
        if (_eventUpdateProfile.value == true) {
            onUpdateProfileFinished()
        }
    }

    override fun onUserInFireStoreCreatedCallback() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}