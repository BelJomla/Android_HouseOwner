package com.android.belJomla.callbacks

import com.android.beljomla.database_classes.Product

interface ProductsCallbacks {

    fun onProductsFetched(products:ArrayList<Product?>)

    fun onProductsFetchingFailed()
}
