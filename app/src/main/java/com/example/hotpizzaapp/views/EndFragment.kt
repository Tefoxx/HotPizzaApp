package com.example.hotpizzaapp.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.hotpizzaapp.R
import com.example.hotpizzaapp.databinding.FragmentEndBinding


class EndFragment : Fragment() {

    private lateinit var binding: FragmentEndBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_end, container,false )
        binding.btnGotoMenu.setOnClickListener {
            parentFragmentManager.popBackStack()
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, MenuFragment())
                commit()
            }
        }
        return binding.root
    }
}