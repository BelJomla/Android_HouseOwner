package com.android.beljomla.database_classes.models


import com.google.firebase.firestore.ServerTimestamp
import java.util.*
import kotlin.collections.ArrayList

class AccumulatedOrder() {

    var accumOrderID:String = ""
    var DPID  = ""
    var WSSID = ""
    var WSSOrderInfo = WSSOrderInfo()
    var HOOrdersInfo  = arrayListOf<HOOrderInfo>()
    var accumOrderState = 0

    @ServerTimestamp
    var date  = Date()

    constructor(
        id: String,
        DP_ID: String,
        WSS_ID: String,
        WSSOrderInfo: WSSOrderInfo,
        HOOrdersInfo: ArrayList<HOOrderInfo>,
        date: Date
    ): this() {
        this.accumOrderID = id
        this.DPID = DP_ID
        this.WSSID = WSS_ID
        this.WSSOrderInfo = WSSOrderInfo
        this.HOOrdersInfo = HOOrdersInfo
        this.date = date
    }
}