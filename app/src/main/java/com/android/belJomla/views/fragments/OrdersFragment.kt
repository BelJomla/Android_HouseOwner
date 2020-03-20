package com.android.belJomla.views.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.android.belJomla.R
import com.android.belJomla.adapters.OrderAdapter
import com.android.belJomla.databinding.FragmentOrdersBinding
import com.android.belJomla.decorators.MarginItemDecoration
import com.android.belJomla.extensions.setAllEnabled
import com.android.belJomla.models.Order
import com.android.belJomla.viewmodels.MainViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [OrdersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OrdersFragment : Fragment() {

    val viewModel by activityViewModels<MainViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentOrdersBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_orders,container,false)



        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.rvOrders.adapter = OrderAdapter(requireContext(),viewModel)


        binding.rvOrders.apply {
            adapter = OrderAdapter(requireContext(),viewModel)
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(MarginItemDecoration(requireContext().resources.getDimension(R.dimen.eight_dp).toInt(),0))

        }

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {isLoading ->
            if (isLoading){
                binding.pbLoading.visibility = View.VISIBLE
                requireView().setAllEnabled(false)


            }
            else {
                binding.pbLoading.visibility = View.GONE
                requireView().setAllEnabled(true)
            }


        })
        viewModel.orders.observe(viewLifecycleOwner, Observer {

            (binding.rvOrders.adapter as OrderAdapter).submitList(it.toMutableList())

        })


        return binding.root
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         *
         * @return A new instance of fragment OrdersFragment.
         */
        @JvmStatic
        fun newInstance() =
            OrdersFragment()
    }
}
