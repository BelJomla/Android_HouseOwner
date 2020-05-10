package com.android.beljomla.database_classes.models

class WSS() {
    var id : String = ""
    var ownerName = ""
    var ownerMobile = ""
    var country = ""
    var city = ""
    var location = Location()


    constructor(id:String,ownerName:String,ownerMobile :String,country:String, city : String, location: Location):this(){
        this.id = id
        this.ownerMobile = ownerMobile
        this.ownerName = ownerName
        this.city = city
        this.country = country
        this.location = location
    }

    override fun toString(): String {
        return "Name $ownerName\n" +
                "mobileNumber $ownerMobile\n"

    }



}