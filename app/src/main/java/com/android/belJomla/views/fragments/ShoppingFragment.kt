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

        //val mActionBar = findViewById<AppBarLayout>(R.id.appbar)
        val navController = findNavController(activity as Activity,R.id.nav_host_fragment)
        //NavigationUI.setupActionBarWithNavController(activity as AppCompatActivity,navController)


        setupRecyclerViews(binding)

        setupObservers(binding)


        binding.user = viewModel.houseOwnerUser.value
        binding.appbar.iv_search.setOnClickListener {
            binding.nestedScrollView.smoothScrollTo(0,0)
        }
        binding.appbar.iv_cart.setOnClickListener {
            it.findNavController().navigate(R.id.action_fragment_shopping_to_cartFragment2)
        }


        return binding.root
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
                binding.rvMainCateg.isEnabled = true
                binding.rvSubCateg.isEnabled = true

            }
            else {
                binding.pbLoading.visibility = View.GONE
                requireView().setAllEnabled(true)
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


            binding.rvMainCateg.adapter!!.notifyDataSetChanged()
            binding.rvSubCateg.adapter!!.notifyDataSetChanged()
        })

        viewModel.category.observe(viewLifecycleOwner, Observer {
            l.logMessage(this,"Category switched to ${it.name}")
            binding.rvMainCateg.adapter!!.notifyDataSetChanged()
            binding.rvSubCateg.adapter!!.notifyDataSetChanged()
            //viewModel.startLoading()
           // Handler().postDelayed({viewModel.getSomeData()},100)
            //viewModel.getProducts()
            /**
             * We dont call getProducts here becuase the sub-category will change and the
             * observer of the sub-category will call getProducts
             */
        })
        viewModel.subCategory.observe(viewLifecycleOwner, Observer {
            l.logMessage(this,"Sub-Category switched to ${it.name}")
            binding.rvSubCateg.adapter!!.notifyDataSetChanged()
           // viewModel.startLoading()
           // Handler().postDelayed({viewModel.getSomeData()},100)


            viewModel.getProducts()
        })
        viewModel.productList.observe(viewLifecycleOwner, Observer {
            binding.rvShopping.adapter!!.notifyDataSetChanged()
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
