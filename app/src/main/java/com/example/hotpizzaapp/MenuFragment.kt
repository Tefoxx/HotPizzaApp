package com.example.hotpizzaapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class MenuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val myFragmentView = inflater.inflate(R.layout.fragment_menu,
            container, false)
        val btnCart = myFragmentView.findViewById<Button>(R.id.btnGoToDetails)

        btnCart.setOnClickListener{
            activity?.let {
                BottomFragment().show(it.supportFragmentManager, "tag")
            }

        }

        return myFragmentView
    }



}