package com.android.belJomla.viewmodels

import android.widget.Toast
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

    private val _eventOrderAdded = MutableLiveData<Boolean>()
    val eventOrderAdded: LiveData<Boolean>
        get() = _eventOrderAdded

    // Todo {Remove this if another solution was found}
    /**
     * The following 2 vars were added as a work-around for the DiffUtil's oldItem = newItem bug
     */
    private var _modifiedCartItemPos : Int = -1
    val modifiedCartItemPos : Int
        get() = _modifiedCartItemPos
    private var _modifiedProductItemPos : Int = -1
    val modifiedProductItemPos : Int
        get() = _modifiedProductItemPos
    private var _deletdOrderItemPos : Int = -1
    val deletdOrderItemPos : Int
        get() = _deletdOrderItemPos


    /**
     * The following var is to know what is the last query for products and avoid
     * doing the same query when the fragments restarts
     */
    private var lastQueryCategories = arrayOf("","")




    init {
        _categories.value = ArrayList()
        _category.value = MainCategory()
        _subCategory.value = Category()
        _searchQuery.value = ""
        _user.value = authRepo.currentHouseOwnerUser
        _cart.value = Cart()
        _orders.value = ArrayList()
        _isLoading.value = true
        _eventOrderAdded.value = false

        getOrders()
        //getAllData()
    }

    override fun onUserFetched(authenticatedHouseOwnerUser: HouseOwnerUser?) {
        _user.value = authenticatedHouseOwnerUser
    }

    override fun onUserInFireStoreCreatedCallback() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    private fun clearProducts(){
        productList.value?.clear()
        _productList.value = _productList.value // Trigger the observer
    }
    fun getProducts() {
        val categoryID = category.value?.id?:""
        val subCategoryID = subCategory.value?.id?:""

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

      _modifiedCartItemPos=  _cart.value?.addToCart(product)?:0
      _modifiedProductItemPos = productList.value?.indexOf(product)?:-1
      _cart.value = _cart.value // This is to trigger the observers
    }

    fun removeAllFromCart(product: Product){
        cart.value?.removeAllFromCart(product)
        _modifiedCartItemPos = -1
        _modifiedProductItemPos = productList.value?.indexOf(product)?:-1
        _cart.value = _cart.value // This is to trigger the observers

    }
    fun removeOneFromCart(product: Product){

        _modifiedCartItemPos = _cart.value?.removeOneFromCart(product)?:-1
        _modifiedProductItemPos = productList.value?.indexOf(product)?:-1
        _cart.value = _cart.value // This is to trigger the observers

    }

    override fun onCategoriesFetched(categories: ArrayList<MainCategory?>) {
        _categories.value = removeHiddenSubcategories(categories)
        _category.value = categories[0]
        _subCategory.value = _category.value!!.subCategories[0]
        stopLoading()
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
        updateLastQueriedCategory(category.value?.id?:"",subCategory.value?.id?:"")
        stopLoading()
    }

    override fun onProductsFechtingFailed() {
        l.logMessage(this,"onProductsFechtingFailed")
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
    private fun generateOrder(selectedLocation: Location, paymentMethod : Int , prepaidAmount : Double = 0.0) : Order{

        val order = Order()
        //order.orderID Will Be Set In The Repository
        order.houseOwnerID = _user.value!!.id
        order.cart = _cart.value!!
        order.totalPrice = _cart.value!!.calculateCartPrice()
        order.discountPrice = _cart.value!!.calculateCartPrice()
        order.coupon = ""
        order.finalPrice = order.totalPrice - order.discountPrice
        order.country = selectedLocation.country
        order.city = selectedLocation.city
        order.deliveryLocation = selectedLocation
        order.orderState = Order.STATE_PENDING

        return order
    }
    fun postOrder(selectedLocation: Location, paymentMethod : Int , prepaidAmount : Double = 0.0){
        val generatedOrder  = generateOrder(selectedLocation, paymentMethod , prepaidAmount )
        startLoading()
        shoppingRepo.postOrder(generatedOrder)

    }

    override fun onOrderPostSuccessful(postedOrder: Order) {

        _deletdOrderItemPos = -1
        _eventOrderAdded.value = true
        orders.value?.add(postedOrder)
        _orders.value = _orders.value
        l.logMessage(this,"Order $postedOrder Posted")
    }
    override fun onOrderPostFailed() {
        stopLoading()
        _deletdOrderItemPos = -1
        l.logErrorMessage(this," Posting Order Failed")
    }

    override fun onOrdersFetched(fetchedOrders: ArrayList<Order?>) {
        //stopLoading()
        _deletdOrderItemPos = -1
        _orders.value = fetchedOrders
    }

    override fun onDeleteOrderFailed() {
        stopLoading()
        _deletdOrderItemPos = -1
    }

    override fun onDeleteOrderSucceeded(order: Order) {
        stopLoading()
        _deletdOrderItemPos = orders.value?.indexOf(order)?:-1
        orders.value?.remove(order)
    }

    private fun getOrders() {
        shoppingRepo.getOrders()
    }
    fun removeOrder(order:Order){
        startLoading()
        shoppingRepo.removeOrder(order)
    }
    fun onEventOrderAddedHandled(){
        _eventOrderAdded.value = false
        stopLoading()
    }
}