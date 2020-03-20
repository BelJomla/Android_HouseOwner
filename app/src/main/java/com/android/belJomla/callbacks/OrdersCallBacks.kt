package com.android.belJomla.callbacks

import com.android.belJomla.models.Order

interface OrdersCallBacks {

    /**
     * This is to update the generated order id when its generated.
     * I made it to solve a bug that now I forget
     */
    fun onOrderIDSet(orderID  :String)
    fun onOrderPostFailed()
    fun onOrdersFetched(fetchedOrders : ArrayList<Order?>)
    fun onDeleteOrderFailed()
}