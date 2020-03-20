package com.android.belJomla.callbacks

import com.android.belJomla.models.Product

interface ProductsCallbacks {

    fun onProductsFetched(products:ArrayList<Product?>)

    fun onProductsFetchingFailed()
}
