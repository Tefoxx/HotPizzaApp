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
import android.util.Log
import androidx.fragment.app.viewModels
import com.example.hotpizzaapp.data.BundleKeys


class MenuFragment : Fragment(R.layout.fragment_menu) {

    private lateinit var binding: FragmentMenuBinding
    private val viewModel: MenuFragmentViewModel by viewModels()

    private lateinit var adapterPizza: PizzaAdapter
    private lateinit var linearLayout: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMenuBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }
    
    @SuppressLint("NotifyDataSetChanged")
    private fun initView() = with(binding){
        requireActivity().let {

            adapterPizza = PizzaAdapter(viewModel.pizzaListOpen){ listItem ->
                val bundle = Bundle()

                with(bundle) {
                    putString(BundleKeys.NAME, listItem.name)
                    putString(BundleKeys.DESCRIPTION, listItem.description)
                    putString(BundleKeys.PRICE, listItem.price.toInt().toString())
                    //first, тк пока что там только 1 ссылка, как будут ещё данные, изменю сразу
                    putString(BundleKeys.IMAGEURL,listItem.imgList.first())
                }

                val bottomSheet = BottomFragment()
                bottomSheet.arguments = bundle
                bottomSheet.show(parentFragmentManager, "tag")
            }

            linearLayout = LinearLayoutManager(it)

            recyclerPizza.layoutManager = linearLayout
            recyclerPizza.adapter = adapterPizza

            viewModel.pizzaListOpen.observe(viewLifecycleOwner, {
                adapterPizza.notifyDataSetChanged()
            })
        }

        imgLoopa.setOnClickListener {
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

        etSearchPizza.addTextChangedListener {
            viewModel.searchPizza(it.toString())
        }

        etSearchPizza.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                tvMenuFragment.visibility = View.VISIBLE
                imgLoopa.visibility = View.VISIBLE
                etSearchPizza.visibility = View.GONE
                
                val imm =
                    requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
                imm?.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
            }
            true
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.pizzaListOpen.value = viewModel.pizzaList.value
    }
}

