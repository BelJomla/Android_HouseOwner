package com.android.belJomla.models

import android.content.Context
import android.os.Build
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.android.belJomla.R
import com.android.belJomla.utils.LoggerUtils as l
import com.android.belJomla.utils.Constants as c
import kotlin.collections.ArrayList


open class Category() {


    var id:String = ""
    var name  = LocalizedName()
    var hidden = false


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

    fun hasSubCategories():Boolean {
        return subCategories.size != 0
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
@BindingAdapter("localized-text")
fun bindLocalizedName(textView: TextView, name : LocalizedName) {
    textView.text = name.getLocalisedName(textView.context)
}
@BindingAdapter("many-localized-text")
fun bindManyLocalizedName(textView: TextView, vararg names : LocalizedName) {
    var compoundText = ""
    if (names.size==1){
        compoundText = names[0].getLocalisedName(textView.context)
    }
    else {
        for (name in names) {
            compoundText = "$compoundText-${name.getLocalisedName(textView.context)}"
        }
    }

    textView.text = compoundText
}