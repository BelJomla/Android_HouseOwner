package com.android.beljomla.database_classes.models

class Route() {

    constructor(orders : ArrayList<Order>):this(){
        for (order in orders){
                for (WSS in order.WSSIDs.withIndex()  ){
                    //WssCartTuples.add(Tuple("",""))
                }
        }
    }
    var WssCartTuples  = ArrayList<Tuple2<WSS, Cart>>()
    var HoCartTuples  = ArrayList<Tuple>()

    // var WssCartTuples  = Tuple(ArrayList<WSS>(),Cart())
    //var HoCartTuples  = Tuple(ArrayList<HouseOwnerUser>(),Cart())


}