package com.android.belJomla.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.belJomla.callbacks.CategoryCallBacks
import com.android.belJomla.callbacks.OrdersCallBacks
import com.android.belJomla.callbacks.ProductsCallbacks
import com.android.belJomla.callbacks.VerificationCallbacks
import com.android.belJomla.models.*
import com.android.belJomla.repositories.UserRepository
import com.android.belJomla.repositories.ShoppingRepository
import com.google.firebase.FirebaseException
import com.android.belJomla.utils.LoggerUtils as l

class MainViewModel: ViewModel() , VerificationCallbacks , CategoryCallBacks, ProductsCallbacks ,
    OrdersCallBacks {

    private val TAG = javaClass.simpleName

    private val authRepo = UserRepository(this)
    private val shoppingRepo = ShoppingRepository(this,this,this)

    private val _user = MutableLiveData<HouseOwnerUser>()
    val houseOwnerUser: LiveData<HouseOwnerUser>
        get() = _user

    private val _isLoading =  MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean>
        get() = _isLoading

    private val _categories = MutableLiveData<ArrayList<MainCategory?>>()
    val categories: LiveData<ArrayList<MainCategory?>>
        get() = _categories


    private val _category = MutableLiveData<MainCategory>()
    val category: LiveData<MainCategory>
        get() = _category

    private val _subCategory = MutableLiveData<Category>()
    val subCategory: LiveData<Category>
        get() = _subCategory

    private val _searchQuery = MutableLiveData<String>()
    val searchQuery: LiveData<String>
        get() = _searchQuery

    private val _productList = MutableLiveData<ArrayList<Product?>>()
    val productList: LiveData<ArrayList<Product?>>
        get() = _productList

    private val _orders = MutableLiveData<ArrayList<Order?>>()
    val orders: LiveData<ArrayList<Order?>>
        get() = _orders

    private val _cart = MutableLiveData<Cart>()
    val cart: LiveData<Cart>
        get() = _cart

 /*   private val _eventOrderAdded = MutableLiveData<Boolean>()
    val eventOrderAdded: LiveData<Boolean>
        get() = _eventOrderAdded
*/
    // Todo {Remove this if another solution was found}
    /**
     * The following 2 vars were added as a work-around for the DiffUtil's oldItem = newItem bug
     */
    /*private var _modifiedCartItemPos : Int = -1
    val modifiedCartItemPos : Int
        get() = _modifiedCartItemPos*/
    private var _modifiedProductItemPos : Int = -1
    val modifiedProductItemPos : Int
        get() = _modifiedProductItemPos
    /*private var _deletdOrderItemPos : Int = -1
    val deletdOrderItemPos : Int
        get() = _deletdOrderItemPos*/


    /**
     * The following var is to know what is the last query for products and avoid
     * doing the same query when the fragments restarts
     */
    private var lastQueryCategories = arrayOf("","")

    /**
     * The following var is the generated order by the user , It gets created the he clicks the checkout button
     * It is important if 2 users entered the app from different devices and while one of the was in the
     * checkout screen, the other has added an order, without this var, the other app will consider his order added too.
     * Which is wrong because it did not.
     */
     var generatedOrder : Order




    init {

        l.logMessage(this,"init")
        _categories.value = ArrayList()
        _category.value = MainCategory()
        _subCategory.value = Category()
        _searchQuery.value = ""
        _user.value = authRepo.currentHouseOwnerUser
        _cart.value = Cart()
        _orders.value = ArrayList()
        _isLoading.value = true
        generatedOrder = Order()
        //_eventOrderAdded.value = false

        getOrders()
        //getAllData()
    }

    override fun onUserFetched(authenticatedHouseOwnerUser: HouseOwnerUser?) {
        _user.value = authenticatedHouseOwnerUser
    }

    override fun onUserInFireStoreCreatedCallback() {
    }

    override fun onVerificationFailed(exception: FirebaseException?) {

    }
    private fun clearProducts(){
        productList.value?.clear()
        _productList.value = _productList.value // Trigger the observer
    }
    fun getProducts() {
        var categoryID = category.value?.id?:""
        var subCategoryID = subCategory.value?.id?:""


        /**
         * If it was the All category, then fetch them all
         */
        if (categoryID =="0"){
            categoryID = ""
            subCategoryID = ""
        }

        if (categories.value?.size?:0 >0 && !isSameAsLastQuery()) {
            startLoading()
            clearProducts()
            if (subCategory.value?.id == "${category.value?.id}_1") {// If the subcategory is all
                shoppingRepo.getProducts(categID = categoryID)
            } else {
                shoppingRepo.getProducts(categoryID, subCategoryID)
            }
        }
    }

    private fun isSameAsLastQuery(): Boolean {
        l.logMessage(this,"isSameAsLastQuery ${category.value?.id == lastQueryCategories[0] && subCategory.value?.id == lastQueryCategories[1]} ")
            return category.value?.id == lastQueryCategories[0] && subCategory.value?.id == lastQueryCategories[1]
    }

    fun startLoading(){
        _isLoading.value = true
    }
    fun stopLoading(){
        _isLoading.value = false
    }

    fun updateCategory(newCategory: MainCategory) {
        _category.value = newCategory
    }

    fun updateSubCategory(newSubCategory: Category) {
        _subCategory.value = newSubCategory

    }

    fun addToCart(product: Product){

     /*_modifiedCartItemPos= */ _cart.value?.addToCart(product)
      _modifiedProductItemPos = productList.value?.indexOf(product)?:-1
      _cart.value = _cart.value // This is to trigger the observers
    }

    fun removeAllFromCart(product: Product){
        cart.value?.removeAllFromCart(product)
      //  _modifiedCartItemPos = -1
        _modifiedProductItemPos = productList.value?.indexOf(product)?:-1
        _cart.value = _cart.value // This is to trigger the observers

    }
    fun removeOneFromCart(product: Product){

       /*_modifiedCartItemPos = */_cart.value?.removeOneFromCart(product)
        _modifiedProductItemPos = productList.value?.indexOf(product)?:-1
        _cart.value = _cart.value // This is to trigger the observers

    }

    override fun onCategoriesFetched(categories: ArrayList<MainCategory?>) {
       l.logMessage(this,"onCategoriesFetched isEmpty : ${categories.isEmpty()} ")
        if (categories.isNotEmpty()) {
           _categories.value = removeHiddenSubcategories(categories)
           _category.value = categories[0]
           if (_category.value?.hasSubCategories() == true) {
               _subCategory.value = _category.value!!.subCategories[0]
           }
            else {
               val allSubCategory = Category()
               allSubCategory.id = "${categories[0]?.id}_1"
               _subCategory.value = allSubCategory

           }       }
        stopLoading()
    }

    override fun onCategoriesChangeDetected(categories: ArrayList<MainCategory?>) {
        _categories.value = removeHiddenSubcategories(categories)

    }

    private fun removeHiddenSubcategories(categories: ArrayList<MainCategory?>) : ArrayList<MainCategory?> {

        for (i in 0 until categories.size){
            categories[i]?.subCategories = categories[i]?.subCategories?.filter {
                !it.hidden
            } as ArrayList<Category>
        }

        return  categories
    }

    override fun onCategoriesFetchedFailed() {
        _categories.value = null
        stopLoading()
    }

    override fun onProductsFetched(products: ArrayList<Product?>) {
        l.logMessage(this,"onProductsFetched : products => $products")
        _productList.value = products
        updateLastQueriedCategory(category.value?.id?:"none", subCategory.value?.id?:"none")
        stopLoading()
    }

    override fun onProductsFetchingFailed() {
        l.logMessage(this,"onProductsFetchingFailed")
        _productList.value = ArrayList()
        stopLoading()
    }

    private fun updateLastQueriedCategory(categoryID: String , subCategoryID: String){
        lastQueryCategories[0] = categoryID
        lastQueryCategories[1] = subCategoryID
    }

    fun clearCart() {
        _cart.value = Cart()
    }

    /**
     * This method uses the available data ( the cart and user info)
     * As well as the entered location and payment type and amount to generate an order
     * Note: This method does not post the order. It just generates it
     */
    private fun generateOrder(selectedLocation: Location, paymentMethod : Int , prepaidAmount : Double = 0.0) {

        val order = Order()
        /**order.orderID Will Be Set In The Repository Then Updated Through
         * The Interface Callback  : onOrderIDSet
         */
        order.houseOwnerID = _user.value!!.id
        order.cart = _cart.value!!
        order.totalPrice = _cart.value!!.calculateCartPrice()
        order.discountPrice = _cart.value!!.calculateCartPrice()
        order.coupon = ""
        order.finalPrice = order.totalPrice - order.discountPrice
        order.country = selectedLocation.country
        order.city = selectedLocation.city
        order.deliveryLocation = selectedLocation
        order.orderState = Order.STATE_NEW

        generatedOrder = order

    }

    override fun onOrderIDSet(orderID: String) {
        generatedOrder.orderID = orderID
    }

    fun postOrder(selectedLocation: Location, paymentMethod : Int , prepaidAmount : Double = 0.0){
        generateOrder(selectedLocation, paymentMethod , prepaidAmount )
        startLoading()
        shoppingRepo.postOrder(generatedOrder)

    }
    fun isOrderPosted(order:Order):Boolean{
        return orders.value?.contains(order)?:false
    }



    override fun onOrderPostFailed() {
        stopLoading()
     //   _deletdOrderItemPos = -1
        l.logErrorMessage(this," Posting Order Failed")
    }

    override fun onOrdersFetched(fetchedOrders: ArrayList<Order?>){
        stopLoading()
     //   _deletdOrderItemPos = -1
       // _eventOrderAdded.value = fetchedOrders.size == (_orders.value?.size?:0)+1
        _orders.value = fetchedOrders
    }

    override fun onDeleteOrderFailed() {
        stopLoading()
      //  _deletdOrderItemPos = -1
    }



    private fun getOrders() {
        shoppingRepo.getOrders()
    }
    fun removeOrder(order:Order){
        startLoading()
        shoppingRepo.removeOrder(order)
    }



    private fun removeCategoriesListener() = shoppingRepo.removeCategoriesListener()
    private fun removeProductsListener() = shoppingRepo.removeProductsListener()
    private fun removeOrdersListener() = shoppingRepo.removeOrdersListener()

    private fun removeAllListeners(){
        removeCategoriesListener()
        removeProductsListener()
        removeOrdersListener()
    }

    override fun onCleared() {
        l.logMessage(this, "cleared")
        removeAllListeners()
        super.onCleared()
    }

}