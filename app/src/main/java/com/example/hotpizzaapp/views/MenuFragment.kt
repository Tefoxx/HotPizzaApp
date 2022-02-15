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
import com.example.hotpizzaapp.data.BundleKeys


class MenuFragment : Fragment() {

    private lateinit var binding: FragmentMenuBinding
    private val viewModel: MenuFragmentViewModel by activityViewModels()
    private lateinit var adapterPizza: PizzaAdapter
    private lateinit var linearLayout: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{

        binding = FragmentMenuBinding.inflate(inflater, container, false)

        activity?.let {

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

            binding.recyclerPizza.layoutManager = linearLayout
            binding.recyclerPizza.adapter = adapterPizza

            viewModel.pizzaListOpen.observe(viewLifecycleOwner, {
                adapterPizza.notifyDataSetChanged()
            })
        }

        //Search Events place
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
        //End Search Events place

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.pizzaListOpen.value = viewModel.pizzaList.value
    }
}

