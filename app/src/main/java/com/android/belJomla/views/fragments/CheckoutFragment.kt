package com.android.belJomla.views.fragments

import android.content.Context
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
import com.android.belJomla.databinding.FragmentCheckoutBinding
import com.android.belJomla.extensions.setAllEnabled
import com.android.belJomla.models.Order
import com.android.belJomla.utils.LoggerUtils as l
import com.android.belJomla.viewmodels.MainViewModel


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CheckoutFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [CheckoutFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CheckoutFragment : Fragment() {

    val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : FragmentCheckoutBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_checkout,container,false)

        initObservers(binding)

        setOnClickListeners(binding)


        return binding.root
    }

    private fun setOnClickListeners(binding: FragmentCheckoutBinding) {
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()

        }
        binding.btnCheckout.setOnClickListener {
            if (viewModel.houseOwnerUser.value?.hasALocation() == true) {
                viewModel.postOrder(binding.location!!, Order.PAYMENT_METHOD_CASH)
            }
            else {
                Toast.makeText(requireContext(),"Please Add A Delivery Location",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initObservers(binding: FragmentCheckoutBinding) {
        viewModel.houseOwnerUser.observe(viewLifecycleOwner, Observer { user ->
            if (user.hasALocation()) {
                binding.location = user.locations[0]
            }
        })
        viewModel.cart.observe(viewLifecycleOwner, Observer { cart ->
            binding.cart = cart
        })
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            //Toast.makeText(requireContext(),"is loading $isLoading",Toast.LENGTH_SHORT).show()
            if (isLoading) {
                binding.pbLoading.visibility = View.VISIBLE
                binding.root.setAllEnabled(false)
            } else {
                binding.pbLoading.visibility = View.GONE
                binding.root.setAllEnabled(true)


            }
        })




        viewModel.orders.observe(viewLifecycleOwner, Observer {
            if (viewModel.isOrderPosted(viewModel.generatedOrder)) {
                /*  If the user is in this page and the
                 Order has been added, go to next page*/
                l.logMessage(this, "orders changed")

                findNavController().navigate(R.id.action_checkoutFragment_to_thankYouFragment)
            }
        })
    }

    override fun onResume() {
        l.logMessage(this,"onResume")


        super.onResume()
    }

    override fun onStart() {
        l.logMessage(this,"onStart")
        super.onStart()
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.

         * @return A new instance of fragment CheckoutFragment.
         */
        @JvmStatic
        fun newInstance() = CheckoutFragment()
    }
}
