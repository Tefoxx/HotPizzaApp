package com.example.hotpizzaapp.views

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotpizzaapp.adapters.PizzaAdapter
import com.example.hotpizzaapp.databinding.FragmentMenuBinding
import com.example.hotpizzaapp.models.MenuFragmentViewModel
import android.app.Activity
import android.content.Context
import androidx.core.widget.addTextChangedListener


class MenuFragment : Fragment() {

    private lateinit var binding: FragmentMenuBinding
    private val viewModel: MenuFragmentViewModel by activityViewModels()
    private lateinit var adapterPizza: PizzaAdapter
    private lateinit var linearLayout: LinearLayoutManager

    override fun onDestroy() {
        super.onDestroy()
        viewModel.compositeDisposable.dispose()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{

        binding = FragmentMenuBinding.inflate(inflater, container, false)
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
            viewModel.searchPizza(it.toString())
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

