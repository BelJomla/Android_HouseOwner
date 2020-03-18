package com.android.belJomla.models

import java.util.*

 class Location(){
     var name :String = ""
     var description : String = ""
     var country: String= ""
     var city:String= ""
     var neighbor:String= ""
     var lat : Double = -0.0
     var long : Double = -0.0
    constructor( name :String, description : String, country: String, city:String, neighbor:String, lat : Double ,  long : Double): this(){
        this.name = name
        this.description = description
        this.country = country
        this.city = city
        this.neighbor = neighbor
        this.lat = lat
        this.long = long

    }

}

data class CreditCard(var holderFirstName : String, var holderLastName : String, var expireDate: Date, var creditCardNumber:Int , var CVV :Int)