package com.android.belJomla.adapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.belJomla.R
import com.android.belJomla.databinding.OrderItemItemBinding
import com.android.belJomla.databinding.OrderListItemBinding
import com.android.belJomla.models.Cart
import com.android.belJomla.models.Order
import com.android.belJomla.viewmodels.MainViewModel
import com.android.belJomla.utils.LoggerUtils as l





class OrderAdapter(var context:Context, val viewModel:MainViewModel) : ListAdapter<Order?,OrderAdapter.OrderViewHolder>(OrderDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {

        return OrderViewHolder(DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.order_list_item,parent
        ,false))

    }

/*    override fun getItemCount(): Int {
        return viewModel.orders.value?.size?:0
    }*/
class OrderDiffCallBack : DiffUtil.ItemCallback<Order?>(){
    override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
        l.logErrorMessage(this,"areItemsTheSame ${oldItem.orderID == newItem.orderID}")
        return  oldItem.orderID == newItem.orderID
    }

    override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
        l.logErrorMessage(this,"areContentsTheSame ${(oldItem.cart.items.size == newItem.cart?.items?.size) && oldItem?.totalPrice == newItem?.totalPrice}")

        val itemEqual = oldItem == newItem
        val totalPriceEqual = oldItem.totalPrice ==newItem.totalPrice
        val discountPriceEqual = oldItem.discountPrice ==newItem.discountPrice
        val finalPriceEqual = oldItem.finalPrice == newItem.finalPrice
        val orderStateEqual = oldItem.orderState ==newItem.orderState
        val locationNameEqual = oldItem.deliveryLocation.name ==newItem.deliveryLocation.name
        val numOfProductsEqual = oldItem.cart.items.size == newItem.cart.items.size
        val couponEqual = oldItem.coupon == newItem.coupon
        val deliveryPeriodEqual = oldItem.deliveryPeriod == newItem.deliveryPeriod

        return itemEqual && totalPriceEqual && discountPriceEqual &&
                finalPriceEqual && orderStateEqual && locationNameEqual &&
                numOfProductsEqual  && couponEqual && deliveryPeriodEqual

    }

}

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = getItem(holder.adapterPosition)//viewModel.orders.value!![holder.adapterPosition]
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

    override fun submitList(list: MutableList<Order?>?) {
        l.logMessage(this, "submitList")
        super.submitList(list)
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
