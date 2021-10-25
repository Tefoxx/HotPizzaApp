package com.example.hotpizzaapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import androidx.fragment.app.activityViewModels
import com.example.hotpizzaapp.databinding.BottomSheetLayoutBinding
import com.example.hotpizzaapp.databinding.FragmentCartBinding
import com.example.hotpizzaapp.models.MenuFragmentViewModel


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