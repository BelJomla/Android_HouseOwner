package com.android.belJomla.views.activities

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.android.belJomla.R
import com.android.belJomla.models.HouseOwnerUser
import com.android.belJomla.utils.LoggerUtils as l
import com.android.belJomla.utils.Constants as c
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


import kotlinx.android.synthetic.main.activity_demo.*

class DemoActivity : AppCompatActivity() {

        val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)
        setSupportActionBar(toolbar)
        val user = HouseOwnerUser()
        user.firstName = "Abdullah"
        user.mobileNumber = "+96694968493"

        l.logMessage(this,"We are running")

        firestore.collection(c.DEMO_USER_PATH).add(user).addOnSuccessListener {
            l.logMessage(this,"Posted Sucessfully")
        }.addOnFailureListener {
            l.logMessage(this,"Posted Failed")

        }


    }

}
