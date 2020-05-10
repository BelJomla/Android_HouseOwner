package com.android.beljomla.database_classes.models

import com.google.firebase.firestore.ServerTimestamp
import java.util.*
import  com.android.beljomla.utils.Constants as c



class DeliveryPersonUser() {


    var mobileNumber = ""
    var id : String = ""
    var email : String = ""
    var firstName : String = ""
    var lastName :String = ""

    @ServerTimestamp
    var startOfWorkingHours  = Date()
    @ServerTimestamp
    var endOfWorkingHours  = Date()
    
    var isWorking : Boolean = false
    var country =""
    var city  = ""
    var salary = 0.0

    val type : Int = c.DELIVERY_PERSON_USER_TYPE


}