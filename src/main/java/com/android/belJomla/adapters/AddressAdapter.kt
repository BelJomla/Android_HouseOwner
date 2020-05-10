package com.android.belJomla.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.belJomla.R
import com.android.belJomla.databinding.AddressListItemBinding
import com.android.beljomla.database_classes.Location

class AddressAdapter(var context: Context) : RecyclerView.Adapter<AddressAdapter.AddressViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        return AddressViewHolder(DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.address_list_item,parent,false))

    }

    override fun getItemCount(): Int {

    return 4
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {

    val location = Location("Home", "King Fahd University Of Petroleum And Minerals", "KSA", "Dhahran", "Student Housing", 22.23, 112.3)
        holder.binding.locatoin = location
    }

    inner class AddressViewHolder(val binding:AddressListItemBinding) : RecyclerView.ViewHolder(binding.root)


}