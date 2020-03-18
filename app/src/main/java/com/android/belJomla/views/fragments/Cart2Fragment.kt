package com.android.belJomla.views.fragments

import android.content.Context
import android.database.DatabaseUtils
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController


import com.android.belJomla.R
import com.android.belJomla.databinding.FragmentCart2Binding
import com.android.belJomla.extensions.setAllEnabled
import com.android.belJomla.models.Order
import com.android.belJomla.viewmodels.MainViewModel


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [Cart2Fragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [Cart2Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Cart2Fragment : Fragment() {

    val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : FragmentCart2Binding = DataBindingUtil.inflate(inflater,R.layout.fragment_cart2,container,false)
        var fragentJustCreated = true
        viewModel.houseOwnerUser.observe(viewLifecycleOwner, Observer {user->
            binding.location = user.locations[0]
        })
        viewModel.cart.observe(viewLifecycleOwner, Observer { cart->
            binding.cart = cart
        })
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            //Toast.makeText(requireContext(),"is loading $isLoading",Toast.LENGTH_SHORT).show()
            if (isLoading){
                binding.pbLoading.visibility = View.VISIBLE
                binding.root.setAllEnabled(false)
            }
            else {
                binding.pbLoading.visibility = View.GONE
                binding.root.setAllEnabled(true)


            }        })


        viewModel.eventOrderAdded.observe(viewLifecycleOwner, Observer { isOrderAdded ->
            if (isOrderAdded){
                findNavController().navigate(R.id.action_cart2Fragment_to_thankYouFragment)
                viewModel.onEventOrderAddedHandled()

            }
        })

       /* viewModel.orders.observe(viewLifecycleOwner, Observer { orders->
            if (orders.size?:0 >0 && !fragentJustCreated){
                *//**If the user is in this page and the orders
                 *Order has been added, go to next page
                 *//*

                findNavController().navigate(R.id.action_cart2Fragment_to_thankYouFragment)
            }
        })*/

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()

        }
        binding.btnCheckout.setOnClickListener {
            viewModel.postOrder(binding.location!!,Order.PAYMENT_METHOD_CASH)
        }


        fragentJustCreated = false
        return binding.root
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.

         * @return A new instance of fragment Cart2Fragment.
         */
        @JvmStatic
        fun newInstance() = Cart2Fragment()
    }
}
