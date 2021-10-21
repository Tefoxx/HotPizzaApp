package com.example.hotpizzaapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import androidx.fragment.app.activityViewModels
import com.example.hotpizzaapp.models.MenuFragmentViewModel


class CartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val myFragmentView = inflater.inflate(R.layout.fragment_cart,
            container, false)
        val btnCCart = myFragmentView.findViewById<Button>(R.id.btnGoToEnd)
        val cartEnd = EndFragment()


        btnCCart.setOnClickListener {
            activity?.let {
                it.supportFragmentManager.popBackStack()
                it.supportFragmentManager.beginTransaction().apply {
                    replace(R.id.flFragment, cartEnd)
                    addToBackStack(null)
                    commit()
                }
            }
        }
        return myFragmentView

    }

}