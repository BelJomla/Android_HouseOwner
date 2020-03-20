package com.android.belJomla.views.fragments


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
import androidx.recyclerview.widget.LinearLayoutManager


import com.android.belJomla.R
import com.android.belJomla.adapters.CartAdapter
import com.android.belJomla.databinding.FragmentCartBinding
import com.android.belJomla.decorators.MarginItemDecoration
import com.android.belJomla.viewmodels.MainViewModel


class CartFragment : Fragment() {

    val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : FragmentCartBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_cart,container,false)

        binding.rvCart.adapter = CartAdapter(requireContext(),viewModel)

       // (binding.rvCart.adapter as CartAdapter).submitList(viewModel.cart.value?.items?.toMutableList())
        binding.rvCart.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCart.addItemDecoration(MarginItemDecoration(requireContext().resources.getDimension(R.dimen.eight_dp).toInt(),0))
        viewModel.cart.observe(viewLifecycleOwner, Observer { cart ->
           /*     binding.cart = cart
            if (viewModel.modifiedCartItemPos != -1) {
                (binding.rvCart.adapter as CartAdapter).notifyItemChanged(viewModel.modifiedCartItemPos)
            }
            else {
                (binding.rvCart.adapter as CartAdapter).submitList(cart.items.toMutableList())
            }*/
            binding.cart = cart
            val clonedCart = cart.clone()
            (binding.rvCart.adapter as CartAdapter).submitList(clonedCart.items)

            Toast.makeText(requireContext(),"Changed",Toast.LENGTH_SHORT).show()
            //(binding.rvCart.adapter as CartAdapter).submitList(null)



        })

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()

        }
        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_cartFragment_to_checkoutFragment)
        }


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
