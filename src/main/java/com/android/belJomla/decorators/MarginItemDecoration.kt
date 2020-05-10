package com.android.belJomla.decorators

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration( private val topMargin: Int,private  val leftMargin: Int = topMargin, private  val bottomMargin: Int = topMargin, private  val rightMargin: Int = leftMargin) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View,
                                parent: RecyclerView, state: RecyclerView.State) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) == 0) {
                top = topMargin
            }
            top = topMargin
            left =  leftMargin
            right = rightMargin
            bottom = bottomMargin
        }
    }
}