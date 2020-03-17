package com.android.belJomla.models

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.android.belJomla.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class Product() {

     var id=""
     var name: String = ""
     var imgURLs= ArrayList<String>()
     var category= ""
     var subCategory=""
    var sellingPrice: Double = 0.0
     var quantativeSize: String = ""
     var qualitiveSize: String = ""


    constructor(
        id: String,
        name: String,
        imgURLs: ArrayList<String>,
        category: String,
        subCategory: String,
        sellingPrice: Double,
        quantativeSize: String,
        qualitiveSize: String
    ) : this() {

        this.id = id
        this.name = name
        this.imgURLs = imgURLs
        this.category = category
        this.subCategory = subCategory
        this.sellingPrice = sellingPrice
        this.quantativeSize = quantativeSize
        this.qualitiveSize = qualitiveSize


    }

    override fun equals(other: Any?): Boolean {
        if (other is Product){
            if (other.id == this.id)
                return true
        }
         return false
    }

    override fun toString(): String {
        return id
    }

    override fun hashCode(): Int {


        return id.hashCode()
    }


}

// important code for loading image here
@BindingAdapter("product_image")
fun loadImage(imageView: ImageView,imgURL : String) {
    Glide.with(imageView.context)
        .setDefaultRequestOptions(
            RequestOptions()
                //.circleCrop()
        )
        .load(imgURL)
        .placeholder(ColorDrawable(ContextCompat.getColor(imageView.context,R.color.sugar_white)))
        .into(imageView)


}


