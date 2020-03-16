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


}

fun ArrayList<CartItem>.cartContains(product: Product):Boolean{
    for (item in listIterator()){
        if (item.product == product)
            return true
    }
    return false
}
fun ArrayList<CartItem>.incrementQuantity(product: Product)  {

    for (item in listIterator()) {
        if (item.product == product) {
            item.quantity++
        }


    }

}

    fun ArrayList<CartItem>.decrementQuantity(product: Product) {
        for (item in listIterator()) {
            if (item.product == product) {
                item.quantity--
                if (item.quantity==0){
                    remove(CartItem(product,-1))
                    break
                }
            }

        }
    }
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


        fun addToCart(product: Product) {
            if (items.cartContains(product)) {
                items.incrementQuantity(product)
            } else {
                items.add(CartItem(product, 1))

            }

        }

        fun removeOneFromCart(product: Product) {
            if (items.cartContains(product)) {
                items.decrementQuantity(product)
            }
        }
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

        fun makeCopy() :Cart{
            val newCart = Cart()
            for (item in items){
                newCart.items.add(item)
            }
            return newCart
        }
    }
