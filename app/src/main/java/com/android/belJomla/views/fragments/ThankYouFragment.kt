package com.android.belJomla.views.fragments


import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController

import com.android.belJomla.R
import com.android.belJomla.databinding.FragmentThankYouBinding
import com.android.belJomla.viewmodels.MainViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [ThankYouFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ThankYouFragment : Fragment() {



   var selfCreationCallback : LifeCycleListener? = null


    val viewModel by activityViewModels<MainViewModel>()
    private val changeScreenHandler = Handler()
    lateinit var chanheScreenRunnable :Runnable


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding : FragmentThankYouBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_thank_you,container,false)


        binding.appName = requireContext().getString(R.string.app_name)
        binding.price = viewModel.cart.value?.calculateCartPrice()


        viewModel.clearCart()
        chanheScreenRunnable = Runnable {
            findNavController().navigate(R.id.action_thankYouFragment_to_fragment_shopping)

        }
            changeScreenHandler.postDelayed(chanheScreenRunnable,5000)



        return binding.root
    }



    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is LifeCycleListener) {
            selfCreationCallback = context

        } else {
            throw RuntimeException("$context must implement FragmentThankyouViewAttachmentListener")
        }
    }

    override fun onResume() {
        selfCreationCallback!!.onThankyouFragmentResumed()
        super.onResume()
    }

    override fun onPause() {
        selfCreationCallback!!.onThankyouFragmentPaused()
        changeScreenHandler.removeCallbacks(chanheScreenRunnable)
        super.onPause()
    }

    override fun onDetach() {
        super.onDetach()
        selfCreationCallback = null
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         *
         * @return A new instance of fragment ThankYouFragment.
         */
        @JvmStatic
        fun newInstance() =
            ThankYouFragment()
    }


    interface LifeCycleListener{
        fun onThankyouFragmentResumed()
        fun onThankyouFragmentPaused()
    }
}
