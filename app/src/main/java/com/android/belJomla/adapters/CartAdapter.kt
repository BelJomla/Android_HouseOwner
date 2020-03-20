package com.android.belJomla.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.belJomla.R
import com.android.belJomla.databinding.CartProductListItemBinding
import com.android.belJomla.models.CartItem
import com.android.belJomla.utils.LoggerUtils as l
import com.android.belJomla.viewmodels.MainViewModel


class CartAdapter(var context:Context ,  var viewModel: MainViewModel) :ListAdapter<CartItem,CartAdapter.CartViewHolder>(
    DiffCallBacks()){

    class DiffCallBacks : DiffUtil.ItemCallback<CartItem>()
    {
        override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
            l.logErrorMessage(this,"items the same ${oldItem.product.id == newItem.product.id}")
            return oldItem.product.id == newItem.product.id
        }

        override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
            l.logErrorMessage(this,"old quantity ${oldItem.quantity} New quantity${newItem.quantity} ")

            l.logErrorMessage(this,"contents the same ${oldItem.quantity == newItem.quantity} and ${oldItem.product.id == newItem.product.id}")

            return (oldItem.quantity == newItem.quantity) && oldItem.product.id == newItem.product.id
        }

    }





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding : CartProductListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.cart_product_list_item,parent,false)


        return CartViewHolder(binding)


    }



/*    override fun getItemCount(): Int {
        val size = viewModel.cart.value?.items?.size
        return size ?: 0



    }*/


    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = getItem(holder.adapterPosition)//viewModel.cart.value?.items?.elementAt(holder.adapterPosition)!!
        holder.binding.cartItem = cartItem
        holder.binding.ivAdd.setOnClickListener {
            viewModel.addToCart(cartItem.product)
        }
        holder.binding.ivRemove.setOnClickListener {
            viewModel.removeOneFromCart(cartItem.product)
        }
        holder.binding.ivRemoveAll.setOnClickListener {
            viewModel.removeAllFromCart(cartItem.product)
        }



    }

    override fun submitList(list: MutableList<CartItem>?) {
        l.logErrorMessage(this,"submitList")

        super.submitList(list)
    }



     inner class  CartViewHolder(var binding: CartProductListItemBinding) : RecyclerView.ViewHolder(binding.root)

}



