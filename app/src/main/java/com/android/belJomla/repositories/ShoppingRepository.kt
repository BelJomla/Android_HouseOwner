package com.android.belJomla.repositories

import android.util.Log
import com.android.belJomla.callbacks.CategoryCallBacks
import com.android.belJomla.callbacks.OrdersCallBacks
import com.android.belJomla.callbacks.ProductsCallbacks
import com.android.belJomla.models.*
import com.android.belJomla.utils.DummyDataUtils
import com.android.belJomla.utils.Constants as c
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.android.belJomla.utils.LoggerUtils as l


/**
 * This repository is responsible of all shopping related aspects. Starting from
 * fetching the categories up to managing orders {And maybe history}
 */
class ShoppingRepository(var categoryCallbacks: CategoryCallBacks,var  productsCallbacks  : ProductsCallbacks,var ordersCallBacks: OrdersCallBacks) {

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

    private var categoriesListener :ListenerRegistration? = null
    private var productsListener :ListenerRegistration? = null
    private var ordersListener :ListenerRegistration? = null

    init {
        l.logMessage(this,"init ")
        addDummyProducts()
        //addDummyCategories() TODO DO NOT OPEN THIS COMMENT
        //getCategories()

        startCategoriesListener()
        startProductListener()
        startOrdersListener()


        //getProducts()
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

     fun getProducts(categID : String = "",subCategID : String = ""){
         l.logErrorMessage(this,"getProducts categID is $categID and subID is $subCategID")

         removeProductsListener()


         if (categID == "" && subCategID =="") { // Get All Products
            firestore.collection(c.PRODUCTS_DB_PATH).get().addOnSuccessListener { snapshot ->
                l.logMessage(this,"getProducts Successful")
                val products = ArrayList<Product?>()
                for (document in snapshot.documents) {
                    products.add(document.toObject(Product::class.java))
                }
               // productsCallbacks.onProductsFetched(products)


            }.addOnFailureListener{
                l.logErrorMessage(this,"Fetching Failed : ${it.message}")
                productsCallbacks.onProductsFetchingFailed()
            }
        }
        else if(categID != "" && subCategID ==""){ // Get All Products Of Certain Category
            firestore.collection(c.PRODUCTS_DB_PATH).whereEqualTo("category",categID).get().addOnSuccessListener { snapshot ->
                l.logMessage(this,"getProducts Successful")

                val products = ArrayList<Product?>()
                for (document in snapshot.documents) {
                    products.add(document.toObject(Product::class.java))
                }
                //productsCallbacks.onProductsFetched(products)


            }.addOnFailureListener{
                l.logErrorMessage(this,"Fetching Failed : ${it.message}")
                productsCallbacks.onProductsFetchingFailed()
            }
        }
        else if (categID != "" && subCategID != ""){ // Get a Sub-Category Of a Certain Category
            firestore.collection(c.PRODUCTS_DB_PATH).whereEqualTo("category",categID).whereEqualTo("subCategory",subCategID).get().addOnSuccessListener { snapshot ->
                l.logMessage(this,"getProducts Successful")

                val products = ArrayList<Product?>()
                for (document in snapshot.documents) {
                    products.add(document.toObject(Product::class.java))
                }
               // productsCallbacks.onProductsFetched(products)


            }.addOnFailureListener{
                l.logErrorMessage(this,"Fetching Failed : ${it.message}")
                productsCallbacks.onProductsFetchingFailed()
            }
        }
         startProductListener(categID,subCategID)
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

        val data = DummyDataUtils.getLocalizedDummyProducts()

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

    fun postOrder(generatedOrder: Order) {
        val ordersRef = firestore.collection(c.ORDERS_DB_PATH)
        val id = ordersRef.document().id
        generatedOrder.orderID = id
        ordersCallBacks.onOrderIDSet(generatedOrder.orderID)
        ordersRef.document(id).set(generatedOrder).addOnSuccessListener {
            l.logMessage(this,"product addition success")
            //ordersCallBacks.onOrderPostSuccessful(generatedOrder)
        }.addOnFailureListener{
            l.logErrorMessage(this,"product addition failure")
           // ordersCallBacks.onOrderPostFailed()
        }

    }

    fun getOrders() {
        val ordersRef = firestore.collection(c.ORDERS_DB_PATH)
        ordersRef.whereEqualTo("houseOwnerID",auth.uid).whereIn("orderState",
            listOf(Order.STATE_NEW,Order.STATE_PENDING,Order.STATE_IN_PROGRESS)).orderBy("date", Query.Direction.DESCENDING).get().addOnSuccessListener {
            l.logMessage(this,"Fetched Order Successfully ${it.toObjects(Order::class.java).size}")
            //ordersCallBacks.onOrdersFetched(it.toObjects(Order::class.java) as ArrayList<Order?>)
        }
    }
    private fun startOrdersListener(){
        l.logMessage(this,"startOrdersListener auth uid is ${auth.uid}")
        val ordersRef = firestore.collection(c.ORDERS_DB_PATH)
        ordersListener = ordersRef.whereEqualTo("houseOwnerID",auth.uid)
            .whereIn("orderState", listOf(Order.STATE_NEW,Order.STATE_PENDING,Order.STATE_IN_PROGRESS))
            .orderBy("date", Query.Direction.DESCENDING)
            .addSnapshotListener { querySnapshot, e ->

            l.logMessage(this,"Order Listener triggered")
            if (e != null){
                l.logErrorMessage(this,"Listen Failed $e")
                ordersCallBacks.onDeleteOrderFailed()
                l.logMessage(this,"Order Listener Error")

                return@addSnapshotListener
            }
            l.logMessage(this,"Order Listener No Error")

            val orders = querySnapshot?.toObjects(Order::class.java)

            ordersCallBacks.onOrdersFetched(orders as ArrayList<Order?>)
        }



    }
    private fun startProductListener(categID : String = "", subCategID : String = ""){
        l.logErrorMessage(this,"startProductListener categID is $categID and subID is $subCategID")

        var productsRef : Query? = null
        if (categID == "" && subCategID =="") { // Get All Products

            productsRef = firestore.collection(c.PRODUCTS_DB_PATH)
        }
        else if(categID != "" && subCategID ==""){ // Get All Products Of Certain Category
            productsRef=  firestore.collection(c.PRODUCTS_DB_PATH).whereEqualTo("category",categID)
        }
        else if (categID != "" && subCategID != "") { // Get a Sub-Category Of a Certain Category
            productsRef = firestore.collection(c.PRODUCTS_DB_PATH).whereEqualTo("category", categID)
                .whereEqualTo("subCategory", subCategID)
        }
        productsListener = productsRef!!.addSnapshotListener { querySnapshot, e ->
                if (e != null){
                    l.logErrorMessage(this,"Listen Failed $e")
                    productsCallbacks.onProductsFetchingFailed()
                    return@addSnapshotListener
                }

                val products = querySnapshot?.toObjects(Product::class.java)

                    productsCallbacks.onProductsFetched(products as ArrayList<Product?>)

            }


    }


    private fun startCategoriesListener(){
        val categsRef = firestore.collection(c.CATEGORIES_DB_PATH)
        categoriesListener = categsRef.whereEqualTo("hidden",false).addSnapshotListener { querySnapshot, e ->
            if (e != null){
                l.logErrorMessage(this,"Listen Failed $e")
                categoryCallbacks.onCategoriesFetchedFailed()
                return@addSnapshotListener
            }

            val categories = querySnapshot?.toObjects(MainCategory::class.java)

            categoryCallbacks.onCategoriesFetched(categories as ArrayList<MainCategory?>)
        }
    }

    fun removeOrder(order : Order){
        val ordersRef = firestore.collection(c.ORDERS_DB_PATH)
        l.logMessage(this, "Deleting Dorder ${order.orderID}")
        ordersRef.document(order.orderID).delete().addOnSuccessListener {
            l.logMessage(this,"Order Deleted Successfully")
            //ordersCallBacks.onDeleteOrderSucceeded(order)
        }.addOnFailureListener{
            l.logErrorMessage(this,"Failed To Delete Order")
            ordersCallBacks.onDeleteOrderFailed()
        }


    }

     fun removeCategoriesListener() = categoriesListener?.remove()
     fun removeProductsListener() {
         l.logMessage(this,"Removing $productsListener")
         productsListener?.remove()}
     fun removeOrdersListener() = ordersListener?.remove()

    fun removeAllListeners(){
        removeCategoriesListener()
        removeProductsListener()
        removeOrdersListener()
    }

}