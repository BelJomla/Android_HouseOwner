package com.android.belJomla.utils

import java.text.SimpleDateFormat
import java.util.*

class DateUtils {
   companion object {
       fun toSimpleString(date: Date) : String {
           val format = SimpleDateFormat("dd/MM/yyy")
           return format.format(date)
       }
   }

}