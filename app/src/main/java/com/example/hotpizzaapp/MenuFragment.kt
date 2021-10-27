package com.example.hotpizzaapp

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotpizzaapp.adapters.PizzaAdapter
import com.example.hotpizzaapp.data.remote.PizzaListItem
import com.example.hotpizzaapp.databinding.FragmentMenuBinding
import com.example.hotpizzaapp.models.MenuFragmentViewModel
import android.app.Activity












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

        viewModel.fetchPizzaList(viewModel.pizzaApi)

        activity?.let {

            adapterPizza = PizzaAdapter(viewModel.pizzaListOpen, parentFragmentManager)

            linearLayout = LinearLayoutManager(it)

            binding.recyclerPizza.layoutManager = linearLayout
            binding.recyclerPizza.adapter = adapterPizza

            viewModel.pizzaListOpen.observe(viewLifecycleOwner, {
                adapterPizza.notifyDataSetChanged()
            })
        }

        binding.imgLoopa.setOnClickListener {
            with(binding){
                tvMenuFragment.visibility = View.INVISIBLE
                etSearchPizza.visibility = View.VISIBLE
                etSearchPizza.text.clear()
                imgLoopa.visibility = View.INVISIBLE
                etSearchPizza.requestFocus()
                val inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.showSoftInput(etSearchPizza, InputMethodManager.SHOW_IMPLICIT)
            }
        }

        binding.etSearchPizza.addTextChangedListener {
            with(binding){

                val resList = mutableListOf<PizzaListItem>()
                val edit = it
                if(it != null) {
                   viewModel?.pizzaList?.value?.forEach {
                        if(it.name.lowercase().contains(edit.toString()))
                            resList.add(it)
                    }
                    viewModel?.pizzaListOpen?.value = resList
                }
            }
        }


        binding.etSearchPizza.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                with(binding) {
                    tvMenuFragment.visibility = View.VISIBLE
                    imgLoopa.visibility = View.VISIBLE
                    etSearchPizza.visibility = View.GONE

                }
                val imm =
                    activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
                imm?.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
            }
            true
        }


        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.pizzaListOpen.value = viewModel.pizzaList.value
    }
}

