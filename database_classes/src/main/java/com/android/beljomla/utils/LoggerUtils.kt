package com.android.beljomla.utils

import android.util.Log

class LoggerUtils {


    companion object {
        fun logErrorMessage(caller: Any, message: String) {
            val tag = caller::class.java.simpleName
            Log.e(tag, message)
        }

        fun logMessage(caller: Any, message: String) {
            val tag = caller::class.java.simpleName
            Log.d(tag, message)
        }
    }

}