package com.android.beljomla.database_classes.models

import android.content.Context
import android.os.Build
import java.util.*
import  com.android.beljomla.utils.Constants as c
import com.android.beljomla.utils.LoggerUtils as l

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
     constructor(lat: Double,long: Double):this(){
         this.lat = lat
         this.long = long

     }
     constructor(lat: Double,long: Double,country: String,city: String):this(lat,long){
         this.country = country
         this.city = city
     }

     override fun equals(other: Any?): Boolean {
         if(other is Location){
             if (this.lat == other.lat  && this.long == other.long)
                 return true
         }
         return false
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

class LocalizedName() {

    var en  = ""
    var ar = ""

    constructor(en:String,ar:String):this(){
        this.en = en
        this.ar = ar
    }
    override fun toString(): String {
        return "{en: $en,\n" +
                "ar: $ar}"
    }

    override fun equals(other: Any?): Boolean {
        if (other is LocalizedName){
            return this.ar == other.ar && this.en == other.en
        }
        return false
    }
    fun getLocalisedName(context : Context) : String{


        val locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.resources.configuration.locales.get(0)
        } else {
            context.resources.configuration.locale
        }
        val localeString = locale.toString()
        l.logMessage(this,"Locale $localeString")
        return if (localeString.startsWith(c.LOCALE_ARABIC)   ){
            ar
        } else {
            en
        }
    }


}