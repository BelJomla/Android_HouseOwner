package com.android.belJomla.application

import android.app.Application
import android.content.Context


class BelJomlAApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext
    }

    companion object{
        fun getAppContext(): Context? {
            return mContext
        }
        private var mContext: Context? = null

    }

}