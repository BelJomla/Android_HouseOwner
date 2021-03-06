package com.android.belJomla.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.android.belJomla.callbacks.VerificationCallbacks
import com.android.belJomla.repositories.UserRepository
import com.android.beljomla.database_classes.HouseOwnerUser
import com.android.belJomla.utils.LoggerUtils as l
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class AuthenticationViewModel :ViewModel(),
     VerificationCallbacks {


    private val TAG = this@AuthenticationViewModel.javaClass.simpleName
    private val repository = UserRepository(this)


    private var _user =  MutableLiveData<HouseOwnerUser>()
    val houseOwnerUser : LiveData<HouseOwnerUser>
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

    private val _eventFirestoreUserCreated =  MutableLiveData<Boolean>()
    val eventFirestoreUserCreated : LiveData<Boolean>
        get() = _eventFirestoreUserCreated

    private val _eventVerificationFailed =  MutableLiveData<Boolean>()
    val eventVerificationFailed : LiveData<Boolean>
        get() = _eventVerificationFailed

    var errorMessage : String? = "error"

     private val _eventVerificationFailedTooManyRequests =  MutableLiveData<Boolean>()
     val eventVerificationFailedTooManyRequests : LiveData<Boolean>
         get() = _eventVerificationFailedTooManyRequests

     private val _eventVerificationInvalidCredentials =  MutableLiveData<Boolean>()
     val eventVerificationInvalidCredentials : LiveData<Boolean>
         get() = _eventVerificationInvalidCredentials

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
        _eventVerificationInvalidCredentials.value = false
        _eventVerificationFailedTooManyRequests.value = false
        _eventVerificationSuccess.value = false
        _eventFirestoreUserCreated.value = false
        _callbacks.value = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     houseOwnerUser action.
                Log.d(TAG, "onVerificationCompleted:$credential")
             //   _eventVerificationSuccess.value = true
                //  stopLoading()
                signInWithPhoneAuthCredential(credential)

            }

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.e(TAG, "onVerificationFailed ${e.localizedMessage}" )


                when (e) {
                    is FirebaseAuthInvalidCredentialsException -> {
                        // Invalid request
                        // ...
                        errorMessage = e.localizedMessage
                        _eventVerificationInvalidCredentials.value = true

                    }
                    is FirebaseTooManyRequestsException -> {
                        // The SMS quota for the project has been exceeded
                        // ...
                        errorMessage = e.localizedMessage
                        _eventVerificationFailedTooManyRequests.value = true


                    }
                    else -> {
                        errorMessage = e.localizedMessage
                        _eventVerificationFailed.value = true


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
                // now need to ask the houseOwnerUser to enter the code and then construct a credential
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
        l.logMessage(this,"startLoading")

        _isLoading.value = true
    }
    fun stopLoading(){
        l.logMessage(this,"stopLoading")
        _isLoading.value = false
    }
    fun onCodeSentComplete(){
        _eventCodeSent.value = false
    }
    fun onCodeResentComplete(){
        _eventCodeResent.value = false
    }

    /**
     * Verification complete means either success or failure
     */
    fun onVerificationComplete(){
        _eventVerificationSuccess.value = false
        _eventVerificationFailed.value = false
        _eventVerificationInvalidCredentials.value = false
        _eventVerificationFailedTooManyRequests.value = false
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
    fun createUserInFirestore(firstName:String ,lastName:String) {
        startLoading()
        return repository.createUserInFirestore(firstName,lastName,houseOwnerUser.value!!.mobileNumber)
    }

    override fun onUserFetched(authenticatedHouseOwnerUser : HouseOwnerUser?) {
        _user.value = authenticatedHouseOwnerUser
        stopLoading()
    }

    override fun onUserInFireStoreCreatedCallback() {

        _eventFirestoreUserCreated.value = true


    }

    override fun onVerificationFailed(exception: FirebaseException?) {
        errorMessage = exception?.localizedMessage
        if (exception!= null) {
            callbacks.value?.onVerificationFailed(exception)
        }
        else {
            _eventVerificationFailed.value = true

        }
    }
     fun onEventUserInFireStoreCreatedHandled(){
         _eventFirestoreUserCreated.value = false
         stopLoading()
     }

}


