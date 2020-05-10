package com.android.belJomla.views.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController


import com.android.belJomla.R


/**
 * A simple [Fragment] subclass.
 * Use the [HistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HistoryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_history, container, false)
        val navController = findNavController()
        val btnBack = view.findViewById<ImageView>(R.id.iv_back)
        btnBack.setOnClickListener {
            navController.navigateUp()
        }

        return view    }


    companion object {

        @JvmStatic
        fun newInstance() =
            HistoryFragment()

    }
}
