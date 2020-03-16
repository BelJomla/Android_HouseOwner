package com.android.belJomla.models

import android.content.Context
import android.os.Build
import com.android.belJomla.utils.Constants as c
import java.util.*
import kotlin.collections.ArrayList


open class Category() {


    var id:String = ""
    var name  = CategoryName()
    var hidden = false
    fun getLocalisedName(context : Context) : String{


        val locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.resources.configuration.locales.get(0)
        } else {
            context.resources.configuration.locale
        }
        val localeString = locale.toString()
        return if (localeString == c.LOCALE_ARABIC ){
            name.ar
        } else {
            name.en
        }
    }

    override fun toString(): String {
        return "{id: $id,\n" +
                "name : $name,\n" +
                "hidden: $hidden\n}"
    }

    override fun equals(other: Any?): Boolean {
        return if (other is MainCategory){
            other.id == this.id
        } else false
    }
}
class MainCategory() : Category() {
    // Context for locale
    var subCategories = ArrayList<Category>()

    override fun toString(): String {
        return super.toString() + ",\nsubCategories :{ \n" +
                "${subCategories}}"
    }

}



  class CategoryName() {

    var en  = ""
    var ar = ""

      override fun toString(): String {
          return "{en: $en,\n" +
                  "ar: $ar}"
      }
}