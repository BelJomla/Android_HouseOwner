package com.android.belJomla.models


import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.android.belJomla.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.firestore.Exclude
import com.android.belJomla.utils.Constants as c

class HouseOwnerUser() {

    @get:Exclude
    var isAuthenticated: Boolean = false
    @get:Exclude
    var isNew: Boolean = false
    @get:Exclude
    var isCreated: Boolean = false
    var mobileNumber = ""

    var id : String = ""
    var email : String = ""
    var firstName : String = ""
    var lastName :String = ""
    var balance : Double = 0.0
    var points : Int = 0
    val type : Int = c.HOUSE_OWNER_USER_TYPE
    var locations :ArrayList<Location> = ArrayList()
    var creditCards : ArrayList<CreditCard> = ArrayList()


    constructor(mobile:String) : this() {
        this.mobileNumber= mobile


    }

    init {
        isAuthenticated = false
        isNew = false
        isCreated = false
    }

    override fun toString(): String {
        return "Name $firstName $lastName\n" +
                "mobileNumber $mobileNumber\n" +
                "isAuth $isAuthenticated"
    }

    fun getFormatedMobile(mobile :String = this.mobileNumber) : String{

        if (mobile.startsWith("+966")){
            return "+966 ${mobile.removePrefix("+966")}"
        }
        else return mobile
    }

    fun hasALocation(): Boolean {
        return locations.size>0
    }
}

// important code for loading image here
@BindingAdapter("profileImage")
fun loadImage(imageView: ImageView, dummy :Int) {
    Glide.with(imageView.context)
        .setDefaultRequestOptions(
            RequestOptions()
            .circleCrop()
        )
        .load(imageView.context.getDrawable(R.drawable.ic_person_white_24dp)).apply(RequestOptions().circleCrop())
        .placeholder(ColorDrawable(Color.BLACK)).apply {  }
        .into(imageView)


}
// important code for loading image here
@BindingAdapter("bind-location")
fun bindLocation(textView: TextView, locations: ArrayList<Location>?) {
        if (locations?.isEmpty() == true){
            textView.text = textView.context.getString(R.string.no_location_added)
        }
        else {
            textView.text = "${locations?.get(0)?.country},${locations?.get(0)?.city}-${locations?.get(0)?.name}"
        }
}
