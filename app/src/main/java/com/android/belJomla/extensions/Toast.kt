package com.android.belJomla.extensions

import android.content.Context
import android.widget.Toast

fun Toast.shortMessage(context:Context, message:CharSequence) {
    Toast.makeText(context,message,Toast.LENGTH_SHORT).show()

}
fun Toast.longMessage(context:Context, message:CharSequence) {
    Toast.makeText(context,message,Toast.LENGTH_LONG).show()

}
