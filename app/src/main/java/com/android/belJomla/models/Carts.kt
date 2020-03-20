package com.android.belJomla.models

class CartItem() {
    var product = Product()
    var quantity = 1
    constructor(product: Product , quantity:Int) :this(){
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

    fun clone() :CartItem {
        return CartItem(product.clone(),quantity)
    }


}

fun ArrayList<CartItem>.cartContains(product: Product):Boolean{
    for (item in listIterator()){
        if (item.product == product)
            return true
    }
    return false
}

/**
 * Increments The quantity. !!Important!! THE VALUE TO BE INCREMENTED MUST BE PREVIOUSLY AVAILABLE IN THE LIST
 * @param product the product that we want to increment its quantity
 * in the cart.
 * @return the index of the incremented item
 * Note: The index is important to notify the adapter with the position of the incremented item
 */
fun ArrayList<CartItem>.incrementQuantity(product: Product) : Int  {
    var i=0

    for (item in listIterator()) {
        if (item.product == product) {
            item.quantity++
            break
        }
       i++

    }
    return  i
}

/**
 * Decrements The quantity.And if the quantity is already available, it removes it
 * @param product the product that we want to decrement its quantity
 * in the cart.
 * @return the index of the decremented item.
 * Notes : 1-The index is important to notify the adapter with the position of the decremented item
 * 2- If the item's quantity hit 0, then it removes it and returns -1 to indicate that its no longer available
 *
 *
 */
    fun ArrayList<CartItem>.decrementQuantity(product: Product) : Int {
        var i=0
        for (item in listIterator()) {
            if (item.product == product) {
                item.quantity--
                if (item.quantity==0){
                    remove(CartItem(product,-1))
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
fun ArrayList<CartItem>.removeAllOf(product: Product) {
    for (item in listIterator()) {
        if (item.product == product) {
            remove(CartItem(product,-1))
            break
        }

    }
}

    class Cart() {
        var items = ArrayList<CartItem>()

        /**
         * @param product : The item we want to add its quantity in the cart.
         * If the item is not in the cart, then we add it to the cart with quantity 0
         * @return An integer value representing the index of the added item.
         */
        fun addToCart(product: Product) : Int{
            var index = 0
            return if (items.cartContains(product)) {
                index=  items.incrementQuantity(product)
                index
            } else {
                items.add(CartItem(product, 1))
                0

            }

        }

        /**
         * @param product  :The item that we want to decrement its quantity from the cart
         * Note : the items.decrementQuantity function will take care of removing that item completely
         * if its quantity reached 0
         *
         */
        fun removeOneFromCart(product: Product) : Int {
            if (items.cartContains(product)) {
                return items.decrementQuantity(product)
            }
            return -1
        }

        /**
         * Removes the item completely from the cart (ArrayList) regardless of its quantity
         * Note  : the method @return Nothing since DiffUtil's areItemsTheSame function can handle this deletion
         */
        fun removeAllFromCart(product: Product){
            items.removeAllOf(product)
        }


        fun cartContains(product: Product): Boolean {
            return items.cartContains(product)
        }

        fun calculateCartPrice(): Double {
            var price = 0.0
            for (cartItem in items) {
                price += (cartItem.product.sellingPrice*cartItem.quantity)
            }
            return price
        }

        fun clone():Cart{
            val clonedCart = Cart()
            for (cartItem in items){
                clonedCart.items.add(cartItem.clone())
            }
            //clonedCart.items = items.toMutableList() as ArrayList<CartItem>
            return  clonedCart
        }


    }
