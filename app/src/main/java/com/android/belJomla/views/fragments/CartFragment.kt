package com.android.belJomla.views.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager


import com.android.belJomla.R
import com.android.belJomla.adapters.CartAdapter
import com.android.belJomla.databinding.FragmentCartBinding
import com.android.belJomla.viewmodels.MainViewModel


class CartFragment : Fragment() {

    val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : FragmentCartBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_cart,container,false)

        binding.rvCart.adapter = CartAdapter(requireContext(),viewModel)
        binding.rvCart.layoutManager = LinearLayoutManager(requireContext())

        viewModel.cart.observe(viewLifecycleOwner, Observer {
            (binding.rvCart.adapter as CartAdapter).notifyDataSetChanged()
        })


        return binding.root
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CartFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CartFragment()
    }
}
