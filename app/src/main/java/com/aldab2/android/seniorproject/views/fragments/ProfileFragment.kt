package com.aldab2.android.seniorproject.views.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

import com.aldab2.android.seniorproject.R

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val listView = view.findViewById<ListView>(R.id.lv_profile)
        val profileArray = context!!.resources.getStringArray(R.array.profile_items_labels)
        listView.adapter = ArrayAdapter<String>(view.context,R.layout.fragment_item,profileArray)
        listView.setOnItemClickListener { _, view, i, _ ->
            Toast.makeText(view.context,"Clicked ${profileArray[i]}",Toast.LENGTH_SHORT).show()
        }
        Toast.makeText(view.context,"Clicked ", Toast.LENGTH_SHORT).show()

        return view
    }


}
