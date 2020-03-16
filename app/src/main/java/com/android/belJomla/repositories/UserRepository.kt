package com.android.belJomla.repositories

import android.util.Log
import com.android.belJomla.models.HouseOwnerUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.android.belJomla.utils.Constants
import com.android.belJomla.utils.Constants.USERS_DB_PATH
import com.android.belJomla.callbacks.VerificationCallbacks
import com.google.firebase.firestore.FirebaseFirestore

/**
 * This repo is responsible for all user related data.
 * Starting from authentication up to updating the user and listening for
 * changed on the db for the authenticated user including updating his data.
 */
class UserRepository(var verifCallbacks: VerificationCallbacks) {


    companion object {
        lateinit var instance : UserRepository
            private set
    }

    val TAG = UserRepository::class.java.simpleName

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
     var  currentHouseOwnerUser : HouseOwnerUser? = null
    private var isNewUser = false

    init {


        if(auth.currentUser!= null){
            getUser()
            startUserListener()

        }
    }


    private fun checkIfUserIsNew(houseOwnerUser: HouseOwnerUser){
        val userRef = firestore.collection(USERS_DB_PATH).document(auth.uid!!)
        isNewUser = false
        userRef.get().addOnCompleteListener { getUserTask ->
            Log.d(TAG, "signInWithCredential_getUserRef:  ")
            if (getUserTask.isSuccessful){
                Log.d(TAG, "signInWithCredential_getUserRef: getUserTask isSuccessful ")
                val userDocument = getUserTask.result
                isNewUser = if (userDocument!!.exists()){
                    Log.d(TAG, "signInWithCredential_getUserRef: userDocument exists! ")

                    Log.d(TAG, "Fname ${userDocument["firstName"].toString()} Lname ${userDocument["firstName"].toString()}")

                    // New houseOwnerUser if fname or lname is empty. else , not new houseOwnerUser
                    userDocument["firstName"].toString().isEmpty() && userDocument["lastName"].toString().isEmpty()
                } else {
                    Log.d(TAG, "signInWithCredential_getUserRef: userDocument does not exist! ")

                    // If document does not exist ( no data in firestore) then he is a new houseOwnerUser
                    true
                }
                Log.d(TAG, "onUserFetched  HouseOwnerUser : $houseOwnerUser\n" +
                        "FirebaseUser ${auth.currentUser?.phoneNumber}")

                houseOwnerUser.isNew = isNewUser
                houseOwnerUser.isCreated = true
                houseOwnerUser.isAuthenticated = true
                verifCallbacks.onUserFetched(houseOwnerUser)

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
                    // Sign in success, update UI with the signed-in houseOwnerUser's information
                    Log.d(TAG, "signInWithCredential:success")
                    startUserListener()

                    val firebaseUser = auth.currentUser
                    Log.d(TAG, "firebase houseOwnerUser : $firebaseUser isNewUser : $isNewUser")

                    if (firebaseUser != null ) {
                       val uid = firebaseUser.uid
                        val name = firebaseUser.displayName
                        val email = firebaseUser.email
                        val mobile = firebaseUser.phoneNumber
                        val user = HouseOwnerUser(auth.currentUser!!.phoneNumber!!)
                        checkIfUserIsNew(user)
                        user.isAuthenticated = true


                    }
                    else {
                        val user = HouseOwnerUser("")
                        user.isNew = isNewUser
                        user.isAuthenticated = true
                        Log.d(TAG, "onUserFetched  HouseOwnerUser : $user\n" +
                                "FirebaseUser ${auth.currentUser?.phoneNumber}")
                        verifCallbacks.onUserFetched(user)
                    }


                    // ...
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        val user = HouseOwnerUser("")
                        user.isAuthenticated = false
                        user.isCreated = false
                        Log.d(TAG, "onUserFetched  HouseOwnerUser : $user\n" +
                                "FirebaseUser ${auth.currentUser?.phoneNumber}")
                        verifCallbacks.onUserFetched(user)


                    }
                }

            }

    }



     fun createUserInFirestore(firstName:String ,lastName:String,mobile:String){
         val usersRef = firestore.collection(Constants.USERS_DB_PATH)
         val uidRef = usersRef.document(auth.currentUser!!.uid)
         val user = HouseOwnerUser(auth.currentUser!!.phoneNumber!!)
         user.firstName = firstName
         user.lastName = lastName
         user.id = auth.uid!!
         uidRef.set(user).addOnCompleteListener{userCreationTask ->
             if (userCreationTask.isSuccessful) {
                 verifCallbacks.onUserInFireStoreCreatedCallback()
                 this.currentHouseOwnerUser = user
                 // Todo SignUp Event Successful
             }
             else {
                 // Todo SignUp Event Failure

                    logErrorMessage("HouseOwnerUser Creation Failure")
             }

         }


     }

    fun getUser(){
        val userRef = firestore.collection(USERS_DB_PATH).document(auth.uid!!)
        isNewUser = false
        userRef.get().addOnCompleteListener { getUserTask ->
            Log.d(TAG, "signInWithCredential_getUserRef:  ")
            if (getUserTask.isSuccessful){
                Log.d(TAG, "signInWithCredential_getUserRef: getUserTask isSuccessful ")
                val userDocument = getUserTask.result
                if (userDocument!!.exists()) {

                    val user = userDocument.toObject(HouseOwnerUser::class.java)
                    verifCallbacks.onUserFetched(user)
                    this.currentHouseOwnerUser = user
                }
                else {
                    logErrorMessage("HouseOwnerUser Does Not Exist!!")
                    verifCallbacks.onUserFetched(HouseOwnerUser(""))
                    this.currentHouseOwnerUser = HouseOwnerUser("")

                }
            /*    isNewUser = if (userDocument!!.exists()){
                    Log.d(TAG, "signInWithCredential_getUserRef: userDocument exists! ")

                    Log.d(TAG, "Fname ${userDocument["firstName"].toString()} Lname ${userDocument["firstName"].toString()}")

                    // New houseOwnerUser if fname or lname is empty. else , not new houseOwnerUser
                    userDocument["firstName"].toString().isEmpty() && userDocument["lastName"].toString().isEmpty()
                } else {
                    Log.d(TAG, "signInWithCredential_getUserRef: userDocument does not exist! ")

                    // If document does not exist ( no data in firestore) then he is a new houseOwnerUser
                    true
                }*/


            }
            else {
                logErrorMessage(getUserTask.exception!!.message)
            }

        }
    }

    fun updateUser(fname: String ="", lname : String = "", email  :String = "") {

        val usersRef = firestore.collection(Constants.USERS_DB_PATH)
        val uidRef = usersRef.document(auth.currentUser!!.uid)
        val user = HouseOwnerUser(auth.currentUser!!.phoneNumber!!)
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
                logErrorMessage("HouseOwnerUser Creation Failure")
            }
        }
    }

    private fun startUserListener() {
        logErrorMessage("Started Listening")
        val usersRef = firestore.collection(Constants.USERS_DB_PATH)
        val currentUserRef = usersRef.document(auth.currentUser!!.uid)
        currentUserRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }
            logErrorMessage("Change ")

            if (snapshot != null && snapshot.exists()) {
                Log.d(TAG, "Current data: ${snapshot.data}")
                val user = snapshot.toObject(HouseOwnerUser::class.java)
                    this.currentHouseOwnerUser = user!!
                verifCallbacks.onUserFetched(user)

            } else {
                Log.d(TAG, "Current data: null")
            }
        }
    }
    private fun logErrorMessage(message:String?){
        Log.e(TAG,message ?: "Error Message Null")
    }



}