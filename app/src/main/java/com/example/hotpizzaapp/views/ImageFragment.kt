package com.example.hotpizzaapp.views

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.hotpizzaapp.R
import com.example.hotpizzaapp.data.BundleKeys
import com.example.hotpizzaapp.databinding.FragmentImageBinding
import com.squareup.picasso.Picasso

class ImageFragment : Fragment() {

    private lateinit var binding: FragmentImageBinding

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentImageBinding.inflate(inflater, container,false )

        Picasso.get().load(arguments?.getString(BundleKeys.IMAGEURL)).into(binding.ivPizzaItem)
        binding.pricePizzaItem.text = arguments?.getString(BundleKeys.PRICE) + "₽"


        binding.btnFragmentImage.setOnClickListener{
            parentFragmentManager.popBackStack()
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, CartFragment())
                addToBackStack(null)
                commit()
            }
        }
        return binding.root
    }
}