package com.android.belJomla.callbacks

import com.android.beljomla.database_classes.MainCategory

interface CategoryCallBacks {

    /**
     * This call-back is used when the category is fetched for the 1st time
     * The ViewModel will use it to set its main and sub categories
     */
    fun onCategoriesFetched(categories : ArrayList<MainCategory?>)

    /**
     * This call-back is used when the category got updated from the  backend- or by the admin
     * The view model should update its data without changing the selected category
     */
    fun onCategoriesChangeDetected(categories : ArrayList<MainCategory?>)

    /**
     * This is called when the system fails in reading the categories or reading
     * The detected changes.
     * The ViewModel should handle it appropriately
     */
    fun onCategoriesFetchedFailed()

}