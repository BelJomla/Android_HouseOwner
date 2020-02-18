package com.android.BelJomla.authentication

import android.util.Log
import androidx.lifecycle.*
import com.android.BelJomla.models.User
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class AuthenticationViewModel :ViewModel(), VerificationCallbacks{


    private val TAG = this@AuthenticationViewModel.javaClass.simpleName
    private val repository = AuthenticationRepository(this)


    private var _user =  MutableLiveData<User>()
    val user : LiveData<User>
        get() = _user

    private val _isLoading =  MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean>
            get() = _isLoading

    private val _eventCodeSent =  MutableLiveData<Boolean>()
    val eventCodeSent : LiveData<Boolean>
        get() = _eventCodeSent

    private val _eventCodeResent =  MutableLiveData<Boolean>()
    val eventCodeResent : LiveData<Boolean>
        get() = _eventCodeResent
    private var codeSentOnce = false

    private val _eventVerificationFailed =  MutableLiveData<Boolean>()
    val eventVerificationFailed : LiveData<Boolean>
        get() = _eventVerificationFailed

    private val _eventVerificationSuccess =  MutableLiveData<Boolean>()
    val eventVerificationSuccess : LiveData<Boolean>
        get() = _eventVerificationSuccess

    private val _phoneNumber = MutableLiveData<String>()
    val phoneNumber : LiveData<String>
            get() = _phoneNumber

    private val _callbacks = MutableLiveData<PhoneAuthProvider.OnVerificationStateChangedCallbacks>()
    val callbacks : LiveData<PhoneAuthProvider.OnVerificationStateChangedCallbacks>
            get() = _callbacks

    private val _storedVerificationId = MutableLiveData<String>()
    val storedVerificationId : LiveData<String>
            get() = _storedVerificationId
    private val _resendToken = MutableLiveData<PhoneAuthProvider.ForceResendingToken>()
    val resendToken : LiveData<PhoneAuthProvider.ForceResendingToken>
            get() = _resendToken


    init {
        _isLoading.value = false
        _eventCodeSent.value = false
        _eventCodeResent.value = false
        codeSentOnce = false
        _eventVerificationFailed.value = false
        _eventVerificationSuccess.value = false
        _callbacks.value = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:$credential")
             //   _eventVerificationSuccess.value = true
                //  stopLoading()
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e)
                _eventVerificationFailed.value = true

                when (e) {
                    is FirebaseAuthInvalidCredentialsException -> {
                        // Invalid request
                        // ...
                    }
                    is FirebaseTooManyRequestsException -> {
                        // The SMS quota for the project has been exceeded
                        // ...
                    }
                    else -> {

                    }
                }
                stopLoading()

                // Show a message and update the UI
                // ...
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:$verificationId")


                _eventCodeSent.value = true

                codeSentOnce = true
                stopLoading()


                // Save verification ID and resending token so we can use them later
                _storedVerificationId.value = verificationId
                _resendToken.value = token

                // ...
            }
        }
    }
    fun startLoading(){
        _isLoading.value = true
    }
    fun stopLoading(){
        _isLoading.value = false
    }
    fun onCodeSentComplete(){
        _eventCodeSent.value = false
    }
    fun onCodeResentComplete(){
        _eventCodeResent.value = false
    }
    /*
    Verification complete means either success or failure
     */
    fun onVerificationComplete(){
        _eventVerificationSuccess.value = false
        _eventVerificationFailed.value = false
    }



    fun setPhoneNumber(phoneNumber : String) {
        _phoneNumber.value = phoneNumber
    }

    fun signInWithPhoneAuthCredential(code:String) {
        startLoading()
        val credential = PhoneAuthProvider.getCredential(_storedVerificationId.value!!,code)

        return repository.signInWithPhoneAuthCredential(credential)
        }
    fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        startLoading()
        return repository.signInWithPhoneAuthCredential(credential)
    }

    override fun onSignInCompleteCallback(authenticatedUser : User?) {
        _user.value = authenticatedUser
        stopLoading()
    }

}


