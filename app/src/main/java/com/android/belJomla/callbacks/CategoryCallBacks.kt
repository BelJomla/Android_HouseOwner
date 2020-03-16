package com.android.belJomla.callbacks

import com.android.belJomla.models.MainCategory

interface CategoryCallBacks {

    fun onCategoriesFetched(categories : ArrayList<MainCategory?>)
    fun onCategoriesFetchedFailed()

}