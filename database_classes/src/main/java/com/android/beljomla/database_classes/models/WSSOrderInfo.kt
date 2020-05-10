package com.android.beljomla.database_classes.models

import java.io.Serializable

class WSSOrderInfo() : Serializable {
    var shopName  =""
    var shopLocation = Location()
    var shopMobile = ""
    var totalPrice = 0.0
    var cart = Cart()
    //var cart = arrayListOf<TMPCart>()
   // var cart = arrayListOf<TMPCart>()
/*    fun toCartItemList():MutableList<CartItem>{
        val result = mutableListOf<CartItem>()
        for(item in cart){

            result.add(CartItem(ProductVtmp(item.cartItem.ID,item.cartItem.Name,item.cartItem.imgUrls,item.cartItem.category,item.cartItem.subCategory,item.cartItem.sellingPrice,item.cartItem.quantativeSize,item.cartItem.qualitiveSize,item.quantity),item.quantity))
        }
        return result
    }*/

    constructor(shopName: String, shopLocation: Location, shopMobile: String/*,cart :ArrayList<TMPCart>*/ , cart: Cart):this() {
        this.shopName = shopName
        this.shopLocation = shopLocation
        this.shopMobile = shopMobile
        this.cart = cart
    }
}