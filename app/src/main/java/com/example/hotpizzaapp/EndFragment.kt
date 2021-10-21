package com.example.hotpizzaapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import com.example.hotpizzaapp.databinding.FragmentEndBinding
import com.example.hotpizzaapp.databinding.FragmentImageBinding


class EndFragment : Fragment() {

    private lateinit var binding: FragmentEndBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_end, container,false )
        binding.btnGotoMenu.setOnClickListener {
            activity?.let {
                it.supportFragmentManager.popBackStack()
                it.supportFragmentManager.beginTransaction().apply {
                    replace(R.id.flFragment, MenuFragment())
                    commit()
                }

            }
        }

        return binding.root
    }
}