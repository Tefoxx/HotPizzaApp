package com.example.hotpizzaapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class ImageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val myFragmentView = inflater.inflate(R.layout.fragment_image,
            container, false)
        val btnCart = myFragmentView.findViewById<Button>(R.id.btnGoToCard)

        btnCart.setOnClickListener{
            activity?.let {
                it.supportFragmentManager.popBackStack()
                //Если поставить коммент, то будет возвращать к картинке
                    it.supportFragmentManager.beginTransaction().apply {
                        replace(R.id.flFragment, CartFragment())
                        addToBackStack(null)
                        commit()
                    }
                }
        }

        return myFragmentView
    }

}