package com.android.belJomla.adapters



import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.belJomla.R
import com.android.belJomla.databinding.ProductListItemBinding
import com.android.beljomla.database_classes.Product
import com.android.belJomla.utils.LoggerUtils as l
import com.android.belJomla.viewmodels.MainViewModel

class ProductsAdapter(var context: Context,var viewModel: MainViewModel) : ListAdapter<Product,ProductsAdapter.ProductViewHolder>(DiffCallBacks()) {

    class DiffCallBacks : DiffUtil.ItemCallback<Product>()
    {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            val itemEqual = oldItem == newItem
            val nameEqual = oldItem.name ==newItem.name
            val qualSizeEqual = oldItem.qualitiveSize ==newItem.qualitiveSize
            val quanSizeEqual = oldItem.quantativeSize == newItem.quantativeSize
            val sellingPriceEqual = oldItem.sellingPrice == newItem.sellingPrice
            val categEqual = oldItem.category == newItem.category
            val subCategEqual = oldItem.subCategory == newItem.subCategory
            val imageEqual = oldItem.imgURLs[0] == newItem.imgURLs[0]



            return  itemEqual && nameEqual &&
                    qualSizeEqual && quanSizeEqual
                    && sellingPriceEqual && categEqual &&
                    subCategEqual && imageEqual
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding : ProductListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.product_list_item,parent,false)

        return   ProductViewHolder(binding)

    }

/*
    override fun getItemCount(): Int {

        return viewModel.productList.value?.size?:0
    }
*/

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        val product = getItem(position)//viewModel.productList.value!![holder.adapterPosition]
        val quantity = viewModel.cart.value?.items?.find { it.product == product }?.quantity?:0

            //.get(product)//getItem(position).quantity
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
                l.logMessage(this,"product ${product.name} add to cart clicked")
                viewModel.addToCart(product)
            }


        }
        else {
            holder.binding.ivCart.visibility = View.GONE
            holder.binding.tvLblAddToCart.visibility = View.GONE

            holder.binding.tvRemove.visibility = View.VISIBLE
            holder.binding.ivAdd.visibility = View.VISIBLE
            holder.binding.ivRemove.visibility = View.VISIBLE
            holder.binding.tvCartCount.visibility = View.VISIBLE

            holder.binding.tvCartCount.text = "$quantity"
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