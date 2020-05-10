package com.android.belJomla.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ServerTimestamp
import java.util.*


class Order() {

    companion object{

        const val STATE_CANCELLED = -1
        const val STATE_NEW = 0
        const val STATE_IN_PROGRESS = 1
        const val STATE_COLLECTED = 2
        const val STATE_COMPLETED = 3

        const val DELIVERY_PERIOD_MORNING = 0
        const val DELIVERY_PERIOD_AFTER_NOON = 0
        const val DELIVERY_PERIOD_NIGHT = 0

        const val PAYMENT_METHOD_CASH = 0
        const val PAYMENT_METHOD_WALLET = 1
        const val PAYMENT_METHOD_CREDIT_CARD = 2
        const val PAYMENT_METHOD_HYBIRD = 3





    }

    var orderID  = ""
    var houseOwnerID = ""
    var deliveryPersonID = ""

    @ServerTimestamp
    var date  = Date()
    @ServerTimestamp
    var time = Timestamp(date)
    var deliveryPeriod = 0
    var cart = Cart()
    var totalPrice =0.0
    var discountPrice = 0.0
    var finalPrice = 0.0
    var payedAmount = 0.0
    var paymentMethod = -1
    var coupon = ""
    var country  =""
    var city = "Dammam"
    var reigon = "reigon1"
    var deliveryLocation = Location("Dammam")
    var WSSLocaiton = arrayListOf<String>()
    var orderState = -1
    var dpName = Name()
    var dpMobile = "0599694259"


    override fun equals(other: Any?): Boolean {
        if (other is Order)
            return this.orderID == other.orderID
        return false
    }

}