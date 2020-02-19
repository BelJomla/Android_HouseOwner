package com.android.belJomla.views.fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import com.android.belJomla.R
import com.android.belJomla.authentication.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val listView = view.findViewById<ListView>(R.id.lv_profile)
        val profileArray = context!!.resources.getStringArray(R.array.profile_items_labels)
        listView.adapter = ArrayAdapter<String>(view.context,R.layout.fragment_item,profileArray) as ListAdapter
        listView.setOnItemClickListener { _, view, i, _ ->
            Toast.makeText(view.context,"Clicked ${profileArray[i]}",Toast.LENGTH_SHORT).show()
        }
        Toast.makeText(view.context,"Clicked ", Toast.LENGTH_SHORT).show()

        val signOutButton = view.findViewById<TextView>(R.id.tv_sign_out)
        signOutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val signOutIntent = Intent(context,LoginActivity::class.java)
            signOutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(signOutIntent)
            activity!!.finish()
        }

        return view
    }


}
