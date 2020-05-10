package com.android.beljomla.database_classes.models

import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.android.beljomla.database_classes.R


import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

open class ProductVtmp() {

     var ID=""
    /// var Name = LocalizedName()
    var Name = ""
     var imgUrls= ""
     var category= ""
     var subCategory=""
    var sellingPrice: Double = 0.0
   //  var quantativeSize = LocalizedName()
    // var qualitiveSize = LocalizedName()
    var quantativeSize = ""
    var qualitiveSize = ""

    // TODO Delete this
    var quantity : Int = 2
    constructor(
        id: String,
        /*Name: LocalizedName,*/
        name : String,
        imgURLs : String,
        /*imgURLs: ArrayList<String>,*/
        category: String,
        subCategory: String,
        sellingPrice: Double,
        quantativeSize : String,
        qualitiveSize : String,
        quantity : Int
        /*quantativeSize: LocalizedName,
        qualitiveSize: LocalizedName*/
    ) : this() {

        this.ID = id
        this.Name = name
        this.imgUrls = imgURLs
        this.category = category
        this.subCategory = subCategory
        this.sellingPrice = sellingPrice
        this.quantativeSize = quantativeSize
        this.qualitiveSize = qualitiveSize
        this.quantity = quantity


    }
    constructor(productVtmp: ProductVtmp) : this(productVtmp.ID,
        productVtmp.Name,
        productVtmp.imgUrls,
        productVtmp.category,
        productVtmp.subCategory,
        productVtmp.sellingPrice,
        productVtmp.quantativeSize,
        productVtmp.qualitiveSize,
        productVtmp.quantity
        )


    override fun equals(other: Any?): Boolean {
        if (other is ProductVtmp){
            if (other.ID == this.ID)
                return true
        }
         return false
    }

    override fun toString(): String {
        return ID
    }

    override fun hashCode(): Int {


        return ID.hashCode()
    }

    fun clone(): ProductVtmp {
        return ProductVtmp(this)

    }


}

class Product() {

    var id=""
    var name = LocalizedName()
    var imgURLs= ArrayList<String>()
    var category= ""
    var subCategory=""
    var sellingPrice: Double = 0.0
    var quantativeSize = LocalizedName()
    var qualitiveSize = LocalizedName()


    constructor(
        id: String,
        name: LocalizedName,
        imgURLs: ArrayList<String>,
        category: String,
        subCategory: String,
        sellingPrice: Double,
        quantativeSize: LocalizedName,
        qualitiveSize: LocalizedName
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
    constructor(product: Product) : this(product.id,
        product.name,
        product.imgURLs,
        product.category,
        product.subCategory,
        product.sellingPrice,
        product.quantativeSize,
        product.qualitiveSize
    )

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

    fun clone(): Product {
        return Product(this)

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
        .centerCrop()
        .placeholder(ColorDrawable(ContextCompat.getColor(imageView.context, R.color.sugar_white)))
        .into(imageView)


}
@BindingAdapter("localized-text")
fun bindLocalizedName(textView: TextView, name : LocalizedName) {
    textView.text = name.getLocalisedName(textView.context)
}


