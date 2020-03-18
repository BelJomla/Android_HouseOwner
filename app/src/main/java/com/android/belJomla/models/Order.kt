package com.android.belJomla.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ServerTimestamp
import java.util.*


class Order() {

    companion object{

        const val STATE_NEW = 0
        const val STATE_PENDING = 1
        const val STATE_IN_PROGRESS = 2
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
    var deliveryPeriod = -1
    var cart = Cart()
    var totalPrice =0.0
    var discountPrice = 0.0
    var finalPrice = 0.0
    var payedAmount = 0.0
    var paymentMethod = -1
    var coupon = ""
    var country  =""
    var city = ""
    var deliveryLocation = Location()
    var WSSLocaiton = arrayListOf<String>()
    var orderState = -1


    override fun equals(other: Any?): Boolean {
        if (other is Order)
            return this.orderID==other.orderID
        return false
    }

}