package com.android.belJomla.adapters



import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.belJomla.R
import com.android.belJomla.databinding.ProductListItemBinding
import com.android.belJomla.utils.LoggerUtils as l
import com.android.belJomla.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.product_list_item.view.*

class ProductsAdapter(var context: Context,var viewModel: MainViewModel) : RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding : ProductListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.product_list_item,parent,false)

        return   ProductViewHolder(binding)



    }

    override fun getItemCount(): Int {

        return viewModel.productList.value?.size?:0
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        val product = viewModel.productList.value!![holder.adapterPosition]
        holder.binding.product = product
        //l.logMessage(this,"${viewModel.cart.value?.get(product)}")
        if(viewModel.cart.value?.cartContains(product!!) == false){
            holder.binding.tvRemove.visibility = View.GONE
            holder.binding.ivAdd.visibility = View.GONE
            holder.binding.ivRemove.visibility = View.GONE
            holder.binding.tvCartCount.visibility = View.GONE

            holder.binding.ivCart.visibility = View.VISIBLE
            holder.binding.tvLblAddToCart.visibility = View.VISIBLE


            holder.binding.btnProduct.isClickable = true
            holder.binding.btnProduct.setOnClickListener {
                viewModel.addToCart(product!!)
            }


        }
        else {
            holder.binding.ivCart.visibility = View.GONE
            holder.binding.tvLblAddToCart.visibility = View.GONE

            holder.binding.tvRemove.visibility = View.VISIBLE
            holder.binding.ivAdd.visibility = View.VISIBLE
            holder.binding.ivRemove.visibility = View.VISIBLE
            holder.binding.tvCartCount.visibility = View.VISIBLE

            val itemCartCnt  = viewModel.cart.value?.items?.find { it.product == product }?.quantity?:0
            holder.binding.tvCartCount.text = "$itemCartCnt"
            holder.binding.ivAdd.setOnClickListener {
                viewModel.addToCart(product!!)
            }
            holder.binding.ivRemove.setOnClickListener {
                viewModel.removeOneFromCart(product!!)
            }
            holder.binding.tvRemove.setOnClickListener{
                viewModel.removeAllFromCart(product!!)
            }
            holder.binding.btnProduct.isClickable = false

        }
    }

    inner  class  ProductViewHolder(var binding: ProductListItemBinding) : RecyclerView.ViewHolder( binding.root) {






    }
}