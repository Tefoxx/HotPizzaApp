package com.example.hotpizzaapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import com.example.hotpizzaapp.data.BundleKeys
import com.example.hotpizzaapp.databinding.FragmentImageBinding
import com.example.hotpizzaapp.databinding.FragmentMenuBinding
import com.squareup.picasso.Picasso

class ImageFragment : Fragment() {

    private lateinit var binding: FragmentImageBinding

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_image, container,false )

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