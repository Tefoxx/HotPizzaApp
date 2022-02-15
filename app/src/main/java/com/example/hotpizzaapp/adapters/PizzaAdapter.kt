package com.example.hotpizzaapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.hotpizzaapp.views.BottomFragment
import com.example.hotpizzaapp.R
import com.example.hotpizzaapp.data.BundleKeys
import com.example.hotpizzaapp.data.remote.PizzaListItem
import com.example.hotpizzaapp.databinding.PizzaItemsBinding
import com.squareup.picasso.Picasso

class PizzaAdapter(private val dataList : MutableLiveData<List<PizzaListItem>?>,
                   private val onClick: (PizzaListItem) -> Unit) : RecyclerView.Adapter<PizzaAdapter.PizzaViewHolder>(){

    inner class PizzaViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val binding = PizzaItemsBinding.bind(view)

        @SuppressLint("SetTextI18n")
        fun bind(listItem: PizzaListItem) = with(binding){
            pizzaTitle.text = listItem.name

            pricePizza.text = binding.root.context.getString(R.string.ruble_price, listItem.price.toInt().toString())
            descriptionPizza.text = listItem.description

            cardView.setOnClickListener{
                onClick.invoke(listItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PizzaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pizza_items, parent, false)
        return PizzaViewHolder(view)
    }

    override fun onBindViewHolder(holder: PizzaViewHolder, position: Int) {

        dataList.value?.let {
            val listItem = it[position]
            holder.bind(listItem)
            Picasso.get().load(listItem.imgList.first()).into(holder.binding.pizzaImage)

        }
    }

    override fun getItemCount() = dataList.value?.size?:0
}