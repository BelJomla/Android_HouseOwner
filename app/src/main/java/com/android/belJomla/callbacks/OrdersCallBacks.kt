package com.android.belJomla.callbacks

import com.android.belJomla.models.Order

interface OrdersCallBacks {

    fun onOrderPostSuccessful(postedOrder : Order)
    fun onOrderPostFailed()
    fun onOrdersFetched(fetchedOrders : ArrayList<Order?>)
    fun onDeleteOrderSucceeded(order: Order)
    fun onDeleteOrderFailed()
}