package com.android.belJomla.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.belJomla.callbacks.CategoryCallBacks
import com.android.belJomla.callbacks.ProductsCallbacks
import com.android.belJomla.callbacks.VerificationCallbacks
import com.android.belJomla.models.*
import com.android.belJomla.repositories.UserRepository
import com.android.belJomla.repositories.ShoppingRepository
import com.android.belJomla.utils.LoggerUtils as l

class MainViewModel: ViewModel() , VerificationCallbacks , CategoryCallBacks, ProductsCallbacks {

    private val TAG = javaClass.simpleName

    private val authRepo = UserRepository(this)
    private val shoppingRepo = ShoppingRepository(this,this)

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

    private val _cart = MutableLiveData<Cart>()
    val cart: LiveData<Cart>
        get() = _cart

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




    init {
        _categories.value = ArrayList()
        _category.value = MainCategory()
        _subCategory.value = Category()
        _searchQuery.value = ""
        _user.value = authRepo.currentHouseOwnerUser
        _cart.value = Cart()


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

        if (categories.value?.size?:0 >0) {
            startLoading()
            clearProducts()
            if (subCategory.value?.id == "${category.value?.id}_1") {// If the subcategory is all
                shoppingRepo.getProducts(categID = categoryID)
            } else {
                shoppingRepo.getProducts(categoryID, subCategoryID)
            }
        }
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
        //l.logMessage(this,"Removing hiddens from $categories")



        /*for (i in 0 until categories.size){
            for (j in 0 until categories[i]?.subCategories!!.size){
                if (categories[i]?.subCategories?.get(j)?.hidden == true){
                    l.logMessage(this,"Removing ${categories[i]?.subCategories?.get(j)}")


                    l.logErrorMessage(this,"${categories[i]?.subCategories?.remove(categories[i]?.subCategories?.get(j))} After Removal : $categories")

                }
            }
        }*/
        l.logMessage(this,"Before Filter $categories")
        for (i in 0 until categories.size){
            categories[i]?.subCategories = categories[i]?.subCategories?.filter {
                !it.hidden
            } as ArrayList<Category>
        }
        l.logMessage(this,"After  Filter $categories")

        return  categories
    }

    override fun onCategoriesFetchedFailed() {
        _categories.value = null
        stopLoading()
    }

    override fun onProductsFetched(products: ArrayList<Product?>) {
        l.logMessage(this,"onProductsFetched : products => $products")
        _productList.value = products
        stopLoading()
    }

    override fun onProductsFechtingFailed() {
        l.logMessage(this,"onProductsFechtingFailed")
        _productList.value = ArrayList()
        stopLoading()
    }

}