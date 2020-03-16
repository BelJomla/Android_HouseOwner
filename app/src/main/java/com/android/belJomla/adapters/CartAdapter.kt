package com.android.belJomla.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.belJomla.R
import com.android.belJomla.databinding.CartProductListItemBinding
import com.android.belJomla.databinding.FragmentCartBinding
import com.android.belJomla.viewmodels.MainViewModel

class CartAdapter(var context:Context ,var  viewModel: MainViewModel) :RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.CartViewHolder {
        val binding : CartProductListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.cart_product_list_item,parent,false)

        return CartViewHolder(binding)


    }

    override fun getItemCount(): Int {
            val size = viewModel.cart.value?.items?.size
        return size ?: 0

    }

    override fun onBindViewHolder(holder: CartAdapter.CartViewHolder, position: Int) {

        val cartItem = viewModel.cart.value?.items?.elementAt(holder.adapterPosition)
        if (cartItem != null){
            // TODO CHANGE THIS MESS!
            val quantity = cartItem.quantity
            holder.binding.product = cartItem.product
            holder.binding.productQuantity.text = "$quantity"
        }
    }

     inner class  CartViewHolder(var binding: CartProductListItemBinding) : RecyclerView.ViewHolder(binding.root)

}