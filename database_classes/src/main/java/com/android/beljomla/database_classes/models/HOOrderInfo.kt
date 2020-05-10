package com.android.beljomla.database_classes.models

import java.io.Serializable

class HOOrderInfo() : Serializable {
    var firstName = ""
    var lastName  = ""
    var deliveryLocation = Location()
    var mobile = ""
    var price = 0.0

    var orderID = ""
    var state = 0
    var cart = Cart()

    constructor(
        firstName: String,
        lastName: String,
        deliveryLocation: Location,
        mobile: String,
        price: Double,
        cart: Cart
    ) :this(){
        this.firstName = firstName
        this.lastName = lastName
        this.deliveryLocation = deliveryLocation
        this.mobile = mobile
        this.price = price
        this.cart = cart
    }

    constructor(
        firstName: String,
        lastName: String,
        deliveryLocation: Location,
        mobile: String,
        price: Double,
        id: String,
        state: Int,
        cart: Cart
    ):this() {
        this.firstName = firstName
        this.lastName = lastName
        this.deliveryLocation = deliveryLocation
        this.mobile = mobile
        this.price = price
        this.orderID = id
        this.state = state
        this.cart = cart
    }

    constructor(orderInfo: HOOrderInfo):this( orderInfo.firstName ,
            orderInfo.lastName ,
            orderInfo.deliveryLocation,
            orderInfo.mobile,
            orderInfo.price ,
            orderInfo.orderID ,
            orderInfo.state ,
            orderInfo.cart )
    // var cart = arrayListOf<TMPCart>()
   // var cart = arrayListOf<TMPCart>()
   /* fun toCartItemList():MutableList<CartItem>{
        val result = mutableListOf<CartItem>()
        for(item in cart){

            result.add(CartItem(ProductVtmp(item.cartItem.ID,item.cartItem.Name,item.cartItem.imgUrls,item.cartItem.category,item.cartItem.subCategory,item.cartItem.sellingPrice,item.cartItem.quantativeSize,item.cartItem.qualitiveSize,item.quantity),item.quantity))
        }
        return result
    }*/


    override fun equals(other: Any?): Boolean {
        if (other is HOOrderInfo){
            return (this.orderID == other.orderID)
        }
        return false
    }
}
