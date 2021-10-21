package com.example.hotpizzaapp

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotpizzaapp.adapters.PizzaAdapter
import com.example.hotpizzaapp.data.PizzaEntity
import com.example.hotpizzaapp.databinding.FragmentMenuBinding
import com.example.hotpizzaapp.models.MenuFragmentViewModel
import androidx.core.content.ContextCompat.getSystemService
import java.util.*


class MenuFragment : Fragment() {

    private lateinit var binding: FragmentMenuBinding
    private val viewModel: MenuFragmentViewModel by activityViewModels()

    private lateinit var adapterPizza: PizzaAdapter
    private lateinit var linearLayout: LinearLayoutManager

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu, container, false)
        binding.viewModel = viewModel

        activity?.let {
            adapterPizza = PizzaAdapter(viewModel.pizzaListOpen, it.supportFragmentManager)
            linearLayout = LinearLayoutManager(it)

            binding.recyclerPizza.layoutManager = linearLayout
            binding.recyclerPizza.adapter = adapterPizza

            viewModel.pizzaListOpen.observe(viewLifecycleOwner, {
                adapterPizza.notifyDataSetChanged()
            })
        }


        //Был спидран по SearchView. Было бы хорошо узнать от опытного разраба, как всё таки сделать правильно всё это, а не эти костыли :(
        binding.imgLoopa.setOnClickListener {

            with(binding){
                tvMenuFragment.visibility = View.INVISIBLE
                searchView.visibility = View.VISIBLE
                imgLoopa.visibility = View.INVISIBLE
                searchView.requestFocus()
                val inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.showSoftInput(searchView, InputMethodManager.SHOW_IMPLICIT)
                searchView.setQuery("", false)
            }

        }



        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                with(binding){
                    tvMenuFragment.visibility = View.VISIBLE
                    imgLoopa.visibility = View.VISIBLE
                    searchView.visibility = View.GONE
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val resList = mutableListOf<PizzaEntity>()

                if(newText != null) {
                    viewModel.pizzaList.forEach {
                        if(it.name.lowercase().contains(newText.lowercase()))
                            resList.add(it)
                    }
                    viewModel.pizzaListOpen.value = resList
                }
                return true
            }
        })



        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.pizzaListOpen.value = viewModel.pizzaList
    }
}

