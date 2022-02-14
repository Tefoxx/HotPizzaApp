package com.example.hotpizzaapp.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.hotpizzaapp.R
import com.example.hotpizzaapp.databinding.FragmentCartBinding


class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container,false )

        binding.btnFragmentCart.setOnClickListener {
            parentFragmentManager.popBackStack()
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, EndFragment())
                addToBackStack(null)
                commit()
            }
        }
        return binding.root

    }
}