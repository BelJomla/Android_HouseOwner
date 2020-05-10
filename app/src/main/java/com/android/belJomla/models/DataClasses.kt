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
     constructor(city: String):this(){
         this.city = city
     }

}

 class CreditCard(){
     var holderFirstName : String = ""
      var holderLastName : String = ""
     var expireDate =  Date()
     var creditCardNumber = 0
     var CVV :Int = 0

    constructor( holderFirstName : String,  holderLastName : String,  expireDate: Date, creditCardNumber:Int ,CVV :Int):this(){
        this.CVV = CVV
        this.creditCardNumber = creditCardNumber
        this.expireDate = expireDate
        this.holderFirstName = holderFirstName
        this.holderLastName = holderLastName

    }
}

class Name() {
    var first = ""
    var last = ""
    var fullName : String
    get() = "$first $last"
        set(value: String) {
            first = value.substring(0,value.indexOf(" "))
            last = value.substring(value.indexOf(" ")+1,value.length)
        }

    constructor(first: String, last: String) :this(){
        this.first = first
        this.last = last
    }


}