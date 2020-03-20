package com.android.belJomla.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.android.belJomla.R
import com.android.belJomla.models.Category
import com.android.belJomla.viewmodels.MainViewModel
import com.android.belJomla.utils.Constants as c
import com.android.belJomla.utils.LoggerUtils as l
import kotlinx.android.synthetic.main.category_not_selected_item.view.*

class CategoryAdapter(var context: Context, var viewModel: MainViewModel,var type : String) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {


       return  CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.category_not_selected_item,parent,false))







    }

    override fun getItemCount(): Int {
        if (type ==c.TYPE_CATEGORY){
            return  viewModel.categories.value?.size?: 0
        }
        else if (type == c.TYPE_SUB_CATEGORY){
           // val mainIndex = viewModel.categories.value?.indexOf(viewModel.category.value)?:0
            val nonHiddenSubCategories = viewModel.category.value?.subCategories?.size
            return nonHiddenSubCategories?:0
            //return 0//viewModel.categories.value?.get(mainIndex)?.subCategories?.size?:0
        }
        return  0

    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {


            val index = holder.adapterPosition
            setCategoryText(holder, index)
            setBackgroundColor(holder)

            holder.itemView.setOnClickListener {


                if (type == c.TYPE_CATEGORY){
                    val selectedCateg = viewModel.categories.value!![index]!!
                    viewModel.updateCategory(selectedCateg)
                    l.logMessage(this,"selected categ id is ${selectedCateg.id} and has categ = ${selectedCateg.hasSubCategories()}")
                    if (selectedCateg.hasSubCategories()) {
                        viewModel.updateSubCategory(viewModel.category.value!!.subCategories[0])
                    }
                    else {
                        val allSubCateg = Category()
                        allSubCateg.id = "${selectedCateg.id}_1"
                        viewModel.updateSubCategory(allSubCateg)
                    }
                }
                else {

                    val selectedSubCateg = viewModel.category.value?.subCategories?.get(index)!!
                    viewModel.updateSubCategory(selectedSubCateg)
                }
            }

    }

    private fun setBackgroundColor(holder: CategoryViewHolder) {
        if (type == c.TYPE_CATEGORY) {
            return if (holder.tvCategory.text == viewModel.category.value?.name?.getLocalisedName(context)) {

                holder.cardView.background =
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.categ_selected_circle
                    )

            } else {
                holder.cardView.background =
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.categ_non_selected_circle
                    )

            }
        }
        else {
            return if (holder.tvCategory.text == viewModel.subCategory.value?.name?.getLocalisedName(context)) {

                holder.cardView.background =
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.categ_selected_circle
                    )
            } else {
                holder.cardView.background =
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.categ_non_selected_circle
                    )


            }
        }
    }

    private fun setCategoryText(
        holder: CategoryViewHolder,
        index: Int
    ) {

        if (type == c.TYPE_CATEGORY)
            holder.tvCategory.text = viewModel.categories.value!![index]!!.name.getLocalisedName(context)
        else {
            holder.tvCategory.text = viewModel.category.value?.subCategories?.get(index)?.name?.getLocalisedName(context)
        }
    }

    /*override fun getItemViewType(position: Int): Int {
        if ( type == c.TYPE_CATEGORY ){
            l.logMessage(this,"position $position")
            return if(position == c.CATEGORIES.indexOf(category)){
                l.logMessage(this,"with type $type --> position = $position and category = $category index = ${c.CATEGORIES.indexOf(category)}")

                c.CATEGORY_SELECTED_VIEW_TYPE
            } else {
                c.CATEGORY_NOT_SELECTED_VIEW_TYPE
            }


        }
        else if(type == c.TYPE_SUB_CATEGORY){
            return if(position == c.categMap[category]!!.indexOf(subCategory)){
                c.CATEGORY_SELECTED_VIEW_TYPE
            }
            else {
                c.CATEGORY_NOT_SELECTED_VIEW_TYPE
            }
        }
        return super.getItemViewType(position)
    }*/

    inner  class  CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val cardView = itemView.card_view
        val tvCategory = itemView.tv_categ




    }
}