package com.android.beljomla.database_classes.models


class CartItem() {
    var product = Product()
    var quantity = 1
    constructor(product: Product, quantity:Int) :this(){
        this.product = product
        this.quantity = quantity
    }
    override fun equals(other: Any?): Boolean {
        if (other is CartItem){
            return other.product.id == this.product.id
        }
        return false
    }

    override fun hashCode(): Int {

        return product.hashCode()
    }

    fun clone() : CartItem {
        return CartItem(product.clone(),quantity)
    }


}

fun ArrayList<CartItem>.cartContains(productVtmp: Product):Boolean{
    for (item in listIterator()){
        if (item.product == productVtmp)
            return true
    }
    return false
}

/**
 * Increments The quantity. !!Important!! THE VALUE TO BE INCREMENTED MUST BE PREVIOUSLY AVAILABLE IN THE LIST
 * @param productVtmp the productVtmp that we want to increment its quantity
 * in the cart.
 * @return the index of the incremented item
 * Note: The index is important to notify the adapter with the position of the incremented item
 */
fun ArrayList<CartItem>.incrementQuantity(productVtmp: Product) : Int  {
    var i=0

    for (item in listIterator()) {
        if (item.product == productVtmp) {
            item.quantity++
            break
        }
       i++

    }
    return  i
}

/**
 * Decrements The quantity.And if the quantity is already available, it removes it
 * @param productVtmp the productVtmp that we want to decrement its quantity
 * in the cart.
 * @return the index of the decremented item.
 * Notes : 1-The index is important to notify the adapter with the position of the decremented item
 * 2- If the item's quantity hit 0, then it removes it and returns -1 to indicate that its no longer available
 *
 *
 */
    fun ArrayList<CartItem>.decrementQuantity(productVtmp: Product) : Int {
        var i=0
        for (item in listIterator()) {
            if (item.product == productVtmp) {
                item.quantity--
                if (item.quantity==0){
                    remove(CartItem(productVtmp,-1))
                    i=-1

                }
                break
            }
            i++
        }
        return i
    }

/**
 * Removes the item completely from the cart (ArrayList) regardless of its quantity
 * Note  : the method @return Nothing since DiffUtil's areItemsTheSame function can handle this deletion
 */
fun ArrayList<CartItem>.removeAllOf(productVtmp: Product) {
    for (item in listIterator()) {
        if (item.product == productVtmp) {
            remove(CartItem(productVtmp,-1))
            break
        }

    }
}

    class Cart() {
        var items = ArrayList<CartItem>()

        /**
         * @param productVtmp : The item we want to add its quantity in the cart.
         * If the item is not in the cart, then we add it to the cart with quantity 0
         * @return An integer value representing the index of the added item.
         */
        fun addToCart(productVtmp: Product) : Int{
            var index = 0
            return if (items.cartContains(productVtmp)) {
                index=  items.incrementQuantity(productVtmp)
                index
            } else {
                items.add(CartItem(productVtmp, 1))
                0

            }

        }

        /**
         * @param productVtmp  :The item that we want to decrement its quantity from the cart
         * Note : the items.decrementQuantity function will take care of removing that item completely
         * if its quantity reached 0
         *
         */
        fun removeOneFromCart(productVtmp: Product) : Int {
            if (items.cartContains(productVtmp)) {
                return items.decrementQuantity(productVtmp)
            }
            return -1
        }

        /**
         * Removes the item completely from the cart (ArrayList) regardless of its quantity
         * Note  : the method @return Nothing since DiffUtil's areItemsTheSame function can handle this deletion
         */
        fun removeAllFromCart(productVtmp: Product){
            items.removeAllOf(productVtmp)
        }


        fun cartContains(productVtmp: Product): Boolean {
            return items.cartContains(productVtmp)
        }

        fun calculateCartPrice(): Double {
            var price = 0.0
            for (cartItem in items) {
                price += (cartItem.product.sellingPrice*cartItem.quantity)
            }
            return price
        }

        fun clone(): Cart {
            val clonedCart = Cart()
            for (cartItem in items){
                clonedCart.items.add(cartItem.clone())
            }
            //clonedCart.items = items.toMutableList() as ArrayList<CartItem>
            return  clonedCart
        }


    }
