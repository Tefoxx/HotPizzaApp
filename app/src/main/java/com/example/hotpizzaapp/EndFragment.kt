package com.example.hotpizzaapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


class EndFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val myFragmentView = inflater.inflate(R.layout.fragment_end,
            container, false)
        val btnCEnd = myFragmentView.findViewById<Button>(R.id.btnGotoMenu)
        val cartMenu = MenuFragment()

        btnCEnd.setOnClickListener {
            activity?.let {
                it.supportFragmentManager.popBackStack()
                it.supportFragmentManager.beginTransaction().apply {
                    replace(R.id.flFragment, cartMenu)
                    commit()
                }

            }
        }

        return myFragmentView
    }
}