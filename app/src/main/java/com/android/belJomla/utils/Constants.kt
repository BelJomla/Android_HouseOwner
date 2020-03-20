package com.android.belJomla.utils

object Constants {

    const val VERIFICATION_SMS_RESEND_SECONDS :Long = 60
    const val PRODUCTS_DB_PATH: String = "localized_products"
    const val CATEGORIES_DB_PATH: String = "categories"
    const val ORDERS_DB_PATH : String = "localized_orders"

    const val LOCALE_ARABIC = "ar"
    const val LOCALE_ENGLISH = "en"

    const val HOUSE_OWNER_USER_TYPE: Int = 0
    const val CATEGORY_SELECTED_VIEW_TYPE: Int = 2
    const val CATEGORY_NOT_SELECTED_VIEW_TYPE: Int = 2
    const val TYPE_SUB_CATEGORY = "type_sub_category"
    const val TYPE_CATEGORY = "type_category"
    const val USERS_DB_PATH = "users"
    const val CODE_MAX_LENGTH = 6
    const val LOGIN_FRAGMENT_TAG = "login_fragment"
    const val VERIFICATION_FRAGMENT_TAG = "verification_fragment"
    const val SIGNUP_FRAGMENT_TAG = "signup_fragment"



    val CATEGORIES= arrayOf("Frozen","Diaries","Kitchen Tools")
    val sub1 = arrayListOf<String>("All","Frozen Seafood","Frozen Meat","Frozen Breads & Doughs", "Other")
    val sub2 = arrayListOf<String>("All","Milk Products","Cheese","Yogurt", "Other")
    val sub3 = arrayListOf<String>("All","Cooking Tools","Cleaning Tools","Auxiliaries", "Other")

    val categMap: HashMap<String,ArrayList<String>> = hashMapOf(CATEGORIES[0] to sub1, CATEGORIES[1] to sub2,CATEGORIES[2] to sub3)

    init {

    }
}