package com.android.belJomla.repositories

import android.util.Log
import com.android.belJomla.callbacks.CategoryCallBacks
import com.android.belJomla.callbacks.ProductsCallbacks
import com.android.belJomla.models.Category
import com.android.belJomla.models.HouseOwnerUser
import com.android.belJomla.models.MainCategory
import com.android.belJomla.models.Product
import com.android.belJomla.utils.Constants as c
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthSettings
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.android.belJomla.utils.LoggerUtils as l


/**
 * This repository is responsible of all shopping related aspects. Starting from
 * fetching the categories up to managing orders {And maybe history}
 */
class ShoppingRepository(var categoryCallbacks: CategoryCallBacks,var  productsCallbacks  : ProductsCallbacks) {

    companion object {
        lateinit var instance: ShoppingRepository
            private set
    }

    val TAG = ShoppingRepository::class.java.simpleName
    val settings = FirebaseFirestoreSettings.Builder()
        .setPersistenceEnabled(false)
        .build()


    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    var currentHouseOwnerUser: HouseOwnerUser? = null

    init {

        //addDummyProducts()
        //addDummyCategories()
        getCategories()
        getProducts()
        //startCategoriesListeners()
    }


    private fun getCategories() {
        val categRef = firestore.collection(c.CATEGORIES_DB_PATH)
        l.logMessage(this, "Searching in ${categRef.path}")
        categRef.whereEqualTo("hidden",false).get().addOnCompleteListener { getCategoriesTask ->
            Log.d(TAG, " getCategories Complete ")
            if (getCategoriesTask.isSuccessful) {
                Log.d(TAG, "getCategories isSuccessful ")
                val categoriesCollection = getCategoriesTask.result
                if (categoriesCollection?.size() != 0) {
                    val categories = ArrayList<MainCategory?>()

                    for (document in categoriesCollection!!.documents) {

                        categories.add(document.toObject(MainCategory::class.java))
                        l.logErrorMessage(this, "Got category ${categories.last()}")

                    }
                    categoryCallbacks.onCategoriesFetched(categories)

                } else {
                    l.logErrorMessage(
                        this,
                        "Categories are empty ${categoriesCollection.documents} "
                    )
                    categoryCallbacks.onCategoriesFetchedFailed()

                }

            } else {
                l.logErrorMessage(this, getCategoriesTask.exception?.message!!)
                categoryCallbacks.onCategoriesFetchedFailed()

            }

        }

    }

    private fun startCategoriesListeners() {
    }
     fun getProducts(categID : String = "",subCategID : String = ""){
         l.logErrorMessage(this,"categID is $categID and subID is $subCategID")
        if (categID == "" && subCategID =="") { // Get All Products
            firestore.collection(c.PRODUCTS_DB_PATH).get().addOnSuccessListener { snapshot ->
                l.logMessage(this,"getProducts Successful")
                val products = ArrayList<Product?>()
                for (document in snapshot.documents) {
                    products.add(document.toObject(Product::class.java))
                }
                productsCallbacks.onProductsFetched(products)


            }.addOnFailureListener{
                l.logErrorMessage(this,"Fetching Failed : ${it.message}")
                productsCallbacks.onProductsFechtingFailed()
            }
        }
        else if(categID != "" && subCategID ==""){ // Get All Products Of Certain Category
            firestore.collection(c.PRODUCTS_DB_PATH).whereEqualTo("category",categID).get().addOnSuccessListener { snapshot ->
                l.logMessage(this,"getProducts Successful")

                val products = ArrayList<Product?>()
                for (document in snapshot.documents) {
                    products.add(document.toObject(Product::class.java))
                }
                productsCallbacks.onProductsFetched(products)


            }.addOnFailureListener{
                l.logErrorMessage(this,"Fetching Failed : ${it.message}")
                productsCallbacks.onProductsFechtingFailed()
            }
        }
        else if (categID != "" && subCategID != ""){ // Get a Sub-Category Of a Certain Category
            firestore.collection(c.PRODUCTS_DB_PATH).whereEqualTo("category",categID).whereEqualTo("subCategory",subCategID).get().addOnSuccessListener { snapshot ->
                l.logMessage(this,"getProducts Successful")

                val products = ArrayList<Product?>()
                for (document in snapshot.documents) {
                    products.add(document.toObject(Product::class.java))
                }
                productsCallbacks.onProductsFetched(products)


            }.addOnFailureListener{
                l.logErrorMessage(this,"Fetching Failed : ${it.message}")
                productsCallbacks.onProductsFechtingFailed()
            }
        }
    }

    private fun addDummyCategories() {

        val CATEGORIES = arrayOf("Frozen", "Diaries", "Kitchen Tools")
        val sub1 = arrayListOf<String>(
            "All",
            "Frozen Seafood",
            "Frozen Meat",
            "Frozen Breads & Doughs",
            "Other"
        )
        val sub2 = arrayListOf<String>("All", "Milk Products", "Cheese", "Yogurt", "Other")
        val sub3 =
            arrayListOf<String>("All", "Cooking Tools", "Cleaning Tools", "Auxiliaries", "Other")
        val categories = ArrayList<MainCategory>()
        var i = 0
        for (categLbl in CATEGORIES) {
            i++
            var sub = sub1
            if (categLbl == CATEGORIES[0]) {
                sub = sub1
            }
            if (categLbl == CATEGORIES[1]) {
                sub = sub2
            }
            if (categLbl == CATEGORIES[2]) {
                sub = sub3
            }

            val category = MainCategory()
            category.id = i.toString()
            category.name.ar = categLbl + "عربي"
            category.name.en = categLbl
            category.hidden = false

            val subCategories = ArrayList<Category>()
            var k = 0
            for (subCategLbl in sub) {
                k++
                val subCategory = Category()
                subCategory.id = "${i}_$k"
                subCategory.name.ar = subCategLbl + "عربي"
                subCategory.name.en = subCategLbl
                subCategory.hidden = false
                subCategories.add(subCategory)
            }

            category.subCategories = subCategories
            categories.add(category)
        }
        l.logMessage(this, "Creation complete... supposed to add now")
        for (categ in categories) {
            l.logMessage(this, "adding $categ")
            firestore.collection(c.CATEGORIES_DB_PATH).document(categ.id).set(categ)
                .addOnCompleteListener {
                    //  l.logMessage(this,"${it.exception!!.message}")
                    l.logMessage(this, "Added Category: $categ ")
                }
        }

    }

    private fun addDummyProducts() {
        val data = ArrayList<Product>()
        val commonUrls = ArrayList<String>()


        commonUrls.add("https://picsum.photos/300/300")
        l.logErrorMessage(this, "catMap : " + com.android.belJomla.utils.Constants.categMap)



        data.add(
            Product(
                "1", "Product1 ", commonUrls, "1",
                "1_2", 22.0, "20 mg", "Small"
            )
        )

        data.add(
            Product(
                "2", "Product2 With a name that is pretty long.", commonUrls, "1",
                "1_2", 22.0, "23 oz", "Small"
            )
        )

        data.add(
            Product(
                "3", "Product3", commonUrls, "1",
                "1_2", 28.0, "21 mg", "Small"
            )
        )

        data.add(
            Product(
                "4", "Product4", commonUrls, "1",
                "1_2", 2.0, "2.2 mg", "Small"
            )
        )


        data.add(
            Product(
                "1", "Product1 ", commonUrls, "1",
                "1_3", 22.0, "20 mg", "Small"
            )
        )

        data.add(
            Product(
                "2", "Product2 With a name that is pretty long.", commonUrls, "1",
                "1_3", 22.0, "23 oz", "Small"
            )
        )


        data.add(
            Product(
                "3", "Product3", commonUrls, "1",
                "1_4", 28.0, "21 mg", "Small"
            )
        )

        data.add(
            Product(
                "4", "Product4", commonUrls, "1",
                "1_5", 2.0, "2.2 mg", "Small"
            )
        )


        data.add(
            Product(
                "5", "Product5", commonUrls, "2",
                "2_2", 222.0, "200 mg", "Small"
            )
        )


        data.add(
            Product(
                "6", "Product6", commonUrls, "2",
                "2_2", 342.0, "155 mg", "Small"
            )
        )

        data.add(
            Product(
                "7", "Product7", commonUrls, "2",
                "2_3", 12.0, "32 mg", "Small"
            )
        )

        data.add(
            Product(
                "8", "Product8", commonUrls, "2",
                "2_3", 43.0, "24 mg", "Small"
            )
        )


        data.add(
            Product(
                "5", "Product5", commonUrls, "2",
                "2_4", 222.0, "200 mg", "Small"
            )
        )


        data.add(
            Product(
                "6", "Product6", commonUrls, "2",
                "2_5", 342.0, "155 mg", "Small"
            )
        )


        data.add(
            Product(
                "7", "Product7", commonUrls, "2",
                "2_5", 12.0, "32 mg", "Small"
            )
        )

        data.add(
            Product(
                "8", "Product8", commonUrls, "2",
                "2_5", 43.0, "24 mg", "Small"
            )
        )


        data.add(
            Product(
                "9", "Product9", commonUrls, "3",
                "3_2", 66.0, "87 mg", "Small"
            )
        )

        data.add(
            Product(
                "10", "Product10", commonUrls, "3",
                "3_3", 1.0, "32g", "Small"
            )
        )

        data.add(
            Product(
                "11", "Product11", commonUrls, "3",
                "3_3", 12.4, "43 mg", "Small"
            )
        )

        data.add(
            Product(
                "12", "Product12", commonUrls, "3",
                "3_3", 22.99, "29 mg", "Small"
            )
        )


        data.add(
            Product(
                "9", "Product9", commonUrls, "3",
                "3_4", 66.0, "87 mg", "Small"
            )
        )

        data.add(
            Product(
                "10", "Product10", commonUrls, "3",
                "3_4", 1.0, "32g", "Small"
            )
        )


        data.add(
            Product(
                "11", "Product11", commonUrls, "3",
                "3_5", 12.4, "43 mg", "Small"
            )
        )

        data.add(
            Product(
                "12", "Product12", commonUrls, "3",
                "3_5", 22.99, "29 mg", "Small"
            )
        )

        l.logMessage(this, "Creation complete... supposed to add now")
        for (product in data) {
            l.logMessage(this, "adding $product")
            firestore.collection(c.PRODUCTS_DB_PATH).document(product.id).set(product)
                .addOnCompleteListener {
                    //  l.logMessage(this,"${it.exception!!.message}")
                    l.logMessage(this, "Added Category: $product ")
                }
        }

    }

}