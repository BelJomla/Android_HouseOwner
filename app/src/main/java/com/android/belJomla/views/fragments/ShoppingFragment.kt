package com.android.belJomla.views.fragments


import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager

import com.android.belJomla.R
import com.android.belJomla.adapters.ProductsAdapter
import com.android.belJomla.adapters.CategoryAdapter
import com.android.belJomla.databinding.FragmentShoppingBinding
import com.android.belJomla.decorators.MarginItemDecoration
import com.android.belJomla.extensions.setAllEnabled
import com.android.belJomla.utils.Constants
import com.android.belJomla.viewmodels.MainViewModel
import com.android.belJomla.utils.LoggerUtils as l
import kotlinx.android.synthetic.main.action_bar_shopping.view.*


class ShoppingFragment : Fragment() {

    val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding : FragmentShoppingBinding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_shopping, container, false)

        setupRecyclerViews(binding)
        setupObservers(binding)
        setOnClickListeners(binding)

        binding.user = viewModel.houseOwnerUser.value



        return binding.root
    }

    private fun setOnClickListeners(binding: FragmentShoppingBinding) {
        binding.appbar.iv_search.setOnClickListener {
            binding.nestedScrollView.smoothScrollTo(0, 0)
        }
        binding.appbar.iv_cart.setOnClickListener {
            it.findNavController().navigate(R.id.action_fragment_shopping_to_cartFragment2)
        }
    }


    private fun setupObservers(binding: FragmentShoppingBinding) {


        viewModel.houseOwnerUser.observe(viewLifecycleOwner, Observer { user ->
            l.logMessage(this,"User Changed with ${user?.locations?.size}")
            binding.user = user
        })


        viewModel.isLoading.observe(viewLifecycleOwner, Observer {isLoading ->
            if (isLoading){
                binding.pbLoading.visibility = View.VISIBLE
                //requireView().setAllEnabled(false)
                binding.rvMainCateg.isEnabled = false
                binding.rvSubCateg.isEnabled = false

            }
            else {
                binding.pbLoading.visibility = View.GONE
                //requireView().setAllEnabled(true)
                binding.rvMainCateg.isEnabled = true
                binding.rvSubCateg.isEnabled = true
            }


        })
        viewModel.categories.observe(viewLifecycleOwner, Observer {
           if (it.size==0){// Hide labels is there are not categories
               binding.tvLblCateg.visibility = View.GONE
               binding.tvLblSubCateg.visibility = View.GONE

           }
            else { // Show the labels if we have categories

               binding.tvLblCateg.visibility = View.VISIBLE
               binding.tvLblSubCateg.visibility = View.VISIBLE

           }

            /**
             * TODO Change Category Adapter to ListAdapter With DiffUtils to handle Category Changes
             */
            binding.rvMainCateg.adapter!!.notifyDataSetChanged()
            binding.rvSubCateg.adapter!!.notifyDataSetChanged()
            binding.nestedScrollView.smoothScrollTo(0,0)
        })

        viewModel.category.observe(viewLifecycleOwner, Observer {
            l.logMessage(this,"Category switched to ${it.name}")
            binding.rvMainCateg.adapter!!.notifyDataSetChanged()
            binding.rvSubCateg.adapter!!.notifyDataSetChanged()

            if (it.hasSubCategories()){
                binding.tvLblSubCateg.visibility = View.VISIBLE
            }
            else {
                binding.tvLblSubCateg.visibility = View.GONE
            }

            /**
             * We dont call getProducts here becuase the sub-category will change and the
             * observer of the sub-category will call getProducts
             */
        })
        viewModel.subCategory.observe(viewLifecycleOwner, Observer {
            l.logMessage(this,"Sub-Category switched to ${it.name}")
            val position = viewModel.category.value?.subCategories?.indexOf(it)?:-1
            if (position!=-1) {
                binding.rvSubCateg.adapter!!.notifyItemChanged(position)
            }
            else {
                binding.rvSubCateg.adapter!!.notifyDataSetChanged()
            }
           // viewModel.startLoading()
           // Handler().postDelayed({viewModel.getSomeData()},100)
            viewModel.getProducts()


        })


        viewModel.productList.observe(viewLifecycleOwner, Observer {

            //binding.rvShopping.adapter!!.notifyDataSetChanged()
            (binding.rvShopping.adapter as ProductsAdapter).submitList(it?.toMutableList()?: ArrayList())
        })
        viewModel.cart.observe(viewLifecycleOwner, Observer {
            l.logMessage(this,"Cart Changed")
            if (viewModel.modifiedProductItemPos != -1) {
                binding.rvShopping.adapter!!.notifyItemChanged(viewModel.modifiedProductItemPos)
            }
            val numItems = viewModel.cart.value?.items?.size?:0
            if (numItems ==0){
                binding.appbar.badge.visibility = View.GONE
            }
            else {
                binding.appbar.badge.visibility = View.VISIBLE
                binding.appbar.badge.text = "$numItems"
            }
        })
    }

    private fun setupRecyclerViews(binding: FragmentShoppingBinding) {
        val categoryDecorater = MarginItemDecoration(
            resources.getDimension(R.dimen.category_decorator_padding).toInt()
        )
        var productDecorater = MarginItemDecoration(
            resources.getDimension(R.dimen.product_decorator_padding).toInt()
        )

        binding.rvShopping.adapter = ProductsAdapter(requireContext(),viewModel)
        binding.rvShopping.layoutManager = GridLayoutManager(context, 2)
        binding.rvShopping.addItemDecoration(productDecorater)
        binding.rvShopping.itemAnimator = null


        binding.rvMainCateg.adapter =
            CategoryAdapter(requireContext(), viewModel, type = Constants.TYPE_CATEGORY)
        binding.rvMainCateg.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvMainCateg.addItemDecoration(categoryDecorater)
        binding.nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { scrollView, scrollX, scrollY, oldScrollX, oldScrollY ->
            val SCROLL_DIRECTION_UP = -1
            if (scrollView!!.canScrollVertically(SCROLL_DIRECTION_UP)) {
                // Show elevation
                binding.appbar.elevation = 50f
            } else {
                // Remove elevation
                binding.appbar.elevation = 0f
            }
        })


        binding.rvSubCateg.adapter =
            CategoryAdapter(requireContext(), viewModel, type = Constants.TYPE_SUB_CATEGORY)
        binding.rvSubCateg.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvSubCateg.addItemDecoration(categoryDecorater)
    }


}
