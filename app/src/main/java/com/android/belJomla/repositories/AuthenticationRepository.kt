package com.android.belJomla.repositories

import android.util.Log
import com.android.belJomla.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.android.belJomla.utils.Constants
import com.android.belJomla.utils.Constants.USERS
import com.android.belJomla.viewmodels.VerificationCallbacks
import com.google.firebase.firestore.FirebaseFirestore


class AuthenticationRepository(var verifCallbacks: VerificationCallbacks) {


    companion object {
        lateinit var instance : AuthenticationRepository
            private set
    }

    val TAG = AuthenticationRepository::class.java.simpleName

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private var isNewUser = false

    fun funa(){}
    private fun checkIfUserIsNew(user: User){
        val userRef = firestore.collection(USERS).document(auth.uid!!)
        isNewUser = false
        userRef.get().addOnCompleteListener { getUserTask ->
            Log.d(TAG, "signInWithCredential_getUserRef:  ")
            if (getUserTask.isSuccessful){
                Log.d(TAG, "signInWithCredential_getUserRef: getUserTask isSuccessful ")
                val userDocument = getUserTask.result
                isNewUser = if (userDocument!!.exists()){
                    Log.d(TAG, "signInWithCredential_getUserRef: userDocument exists! ")

                    Log.d(TAG, "Fname ${userDocument["firstName"].toString()} Lname ${userDocument["firstName"].toString()}")

                    // New user if fname or lname is empty. else , not new user
                    userDocument["firstName"].toString().isEmpty() && userDocument["lastName"].toString().isEmpty()
                } else {
                    Log.d(TAG, "signInWithCredential_getUserRef: userDocument does not exist! ")

                    // If document does not exist ( no data in firestore) then he is a new user
                    true
                }
                Log.d(TAG, "onUserFetched  User : $user\n" +
                        "FirebaseUser ${auth.currentUser?.phoneNumber}")

                user.isNew = isNewUser
                user.isCreated = true
                user.isAuthenticated = true
                verifCallbacks.onUserFetched(user)

            }
            else {
                logErrorMessage(getUserTask.exception!!.message)
            }

        }

    }
    fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential)  {



        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")


                    val firebaseUser = auth.currentUser
                    Log.d(TAG, "firebase user : $firebaseUser isNewUser : $isNewUser")

                    if (firebaseUser != null ) {
                       val uid = firebaseUser.uid
                        val name = firebaseUser.displayName
                        val email = firebaseUser.email
                        val mobile = firebaseUser.phoneNumber
                        val user = User(auth.currentUser!!.phoneNumber!!)
                        user.firstName = name!!
                        user.lastName = name
                        checkIfUserIsNew(user)
                        user.isAuthenticated = true


                    }
                    else {
                        val user = User("")
                        user.isNew = isNewUser
                        user.isAuthenticated = true
                        Log.d(TAG, "onUserFetched  User : $user\n" +
                                "FirebaseUser ${auth.currentUser?.phoneNumber}")
                        verifCallbacks.onUserFetched(user)
                    }


                    // ...
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        val user = User("")
                        user.isAuthenticated = false
                        user.isCreated = false
                        Log.d(TAG, "onUserFetched  User : $user\n" +
                                "FirebaseUser ${auth.currentUser?.phoneNumber}")
                        verifCallbacks.onUserFetched(user)


                    }
                }

            }

    }



     fun createUserInFirestore(firstName:String ,lastName:String,mobile:String){
         val usersRef = firestore.collection(Constants.USERS)
         val uidRef = usersRef.document(auth.currentUser!!.uid)
         val user = User(auth.currentUser!!.phoneNumber!!)
         user.firstName = firstName
         user.lastName = lastName
         uidRef.set(user).addOnCompleteListener{userCreationTask ->
             if (userCreationTask.isSuccessful) {
                 verifCallbacks.onUserInFireStoreCreatedCallback()
                 // Todo SignUp Event Successful
             }
             else {
                 // Todo SignUp Event Failure

                    logErrorMessage("User Creation Failure")
             }

         }


     }

    fun getUser(){
        val userRef = firestore.collection(USERS).document(auth.uid!!)
        isNewUser = false
        userRef.get().addOnCompleteListener { getUserTask ->
            Log.d(TAG, "signInWithCredential_getUserRef:  ")
            if (getUserTask.isSuccessful){
                Log.d(TAG, "signInWithCredential_getUserRef: getUserTask isSuccessful ")
                val userDocument = getUserTask.result
                if (userDocument!!.exists()) {
                    val fname = userDocument["firstName"].toString()
                    val lname = userDocument["lastName"].toString()
                    val email = userDocument["email"].toString()
                    val mobile = userDocument["mobile"].toString()
                    val user = User(auth.currentUser!!.phoneNumber!!)
                    user.firstName = fname
                    user.lastName = lname
                    user.email = email
                    verifCallbacks.onUserFetched(user)
                }
                else {
                    logErrorMessage("User Does Not Exist!!")
                    verifCallbacks.onUserFetched(User(""))

                }
            /*    isNewUser = if (userDocument!!.exists()){
                    Log.d(TAG, "signInWithCredential_getUserRef: userDocument exists! ")

                    Log.d(TAG, "Fname ${userDocument["firstName"].toString()} Lname ${userDocument["firstName"].toString()}")

                    // New user if fname or lname is empty. else , not new user
                    userDocument["firstName"].toString().isEmpty() && userDocument["lastName"].toString().isEmpty()
                } else {
                    Log.d(TAG, "signInWithCredential_getUserRef: userDocument does not exist! ")

                    // If document does not exist ( no data in firestore) then he is a new user
                    true
                }*/


            }
            else {
                logErrorMessage(getUserTask.exception!!.message)
            }

        }
    }

    fun updateUser(fname: String ="", lname : String = "", email  :String = "") {

        val usersRef = firestore.collection(Constants.USERS)
        val uidRef = usersRef.document(auth.currentUser!!.uid)
        val user = User(auth.currentUser!!.phoneNumber!!)
        user.firstName = fname
        user.lastName = lname
        user.email = email
        uidRef.set(user).addOnCompleteListener { userCreationTask ->
            if (userCreationTask.isSuccessful) {
                getUser()
                // Todo SignUp Event Successful
            } else {
                // Todo SignUp Event Failure
                getUser()
                logErrorMessage("User Creation Failure")
            }
        }
    }

    private fun logErrorMessage(message:String?){
        Log.e(TAG,message ?: "Error Message Null")
    }



}