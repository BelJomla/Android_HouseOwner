package com.android.belJomla.adapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.belJomla.R
import com.android.belJomla.databinding.OrderItemItemBinding
import com.android.belJomla.databinding.OrderListItemBinding
import com.android.belJomla.models.Cart
import com.android.belJomla.models.Order
import com.android.belJomla.viewmodels.MainViewModel

class OrderAdapter(var context:Context,/*var orders : ArrayList<Order?>,*/ val viewModel:MainViewModel) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {

        return OrderViewHolder(DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.order_list_item,parent
        ,false))

    }

    override fun getItemCount(): Int {
        return viewModel.orders.value?.size?:0
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = viewModel.orders.value!![holder.adapterPosition]
        holder.binding.order = order

        when {
            order?.orderState == Order.STATE_NEW  -> {
                setNewStateVisibilities(holder.binding)
            }
            order?.orderState == Order.STATE_PENDING  -> {
                setPendingStateVisibilities(holder.binding)
            }
            order?.orderState == Order.STATE_IN_PROGRESS  -> {
                setInProgressStateVisibilities(holder.binding)
            }

        }

        holder.binding.rvOrderItems.apply{
            layoutManager = LinearLayoutManager(context)
            adapter= OrderItemAdapter(context,order?.cart?:Cart())


        }
        holder.binding.tvBtnCancel.setOnClickListener {
            AlertDialog.Builder(context).setTitle(context.getString(R.string.delete_order))
                .setMessage(context.getString(R.string.are_you_sure_delete))
                .setPositiveButton(context.getString(R.string.yes)) { it,_ ->
                    it.dismiss()
                    viewModel.removeOrder(order!!)


                }
                .setNegativeButton(context.getString(R.string.cancel)) { it,_ ->
                    it.dismiss()



                }.create().show()
        }


    }

    private fun setInProgressStateVisibilities(binding: OrderListItemBinding) {
        binding.ivBtnCall.visibility = View.VISIBLE
    }

    private fun setPendingStateVisibilities(binding: OrderListItemBinding) {
        binding.tvBtnCancel.visibility = View.VISIBLE

    }

    private fun setNewStateVisibilities(binding: OrderListItemBinding) {
        binding.btnEdit.visibility = View.VISIBLE
        binding.tvBtnCancel.visibility = View.VISIBLE


    }


    inner class OrderViewHolder(var binding: OrderListItemBinding) : RecyclerView.ViewHolder(binding.root)
}

class OrderItemAdapter(var context: Context,var cart: Cart) : RecyclerView.Adapter<OrderItemAdapter.OrderItemViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderItemAdapter.OrderItemViewHolder {
        return OrderItemViewHolder(DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.order_item_item,parent
            ,false))    }

    override fun getItemCount(): Int {

        return cart.items.size
    }

    override fun onBindViewHolder(holder: OrderItemAdapter.OrderItemViewHolder, position: Int) {

       holder.binding.item = cart.items[holder.adapterPosition]
    }


    inner class OrderItemViewHolder(var binding: OrderItemItemBinding) : RecyclerView.ViewHolder(binding.root)
}