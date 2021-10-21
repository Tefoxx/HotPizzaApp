package com.example.hotpizzaapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.hotpizzaapp.BottomFragment
import com.example.hotpizzaapp.R
import com.example.hotpizzaapp.data.PizzaEntity
import com.example.hotpizzaapp.databinding.PizzaItemsBinding
import com.squareup.picasso.Picasso


//                   private val context: Context
class PizzaAdapter(private val dataList : MutableLiveData<List<PizzaEntity>>,
                   private val fragmentManager: FragmentManager) : RecyclerView.Adapter<PizzaAdapter.PizzaViewHolder>(){

    class PizzaViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val binding = PizzaItemsBinding.bind(view)

        @SuppressLint("SetTextI18n")
        fun bind(listItem: PizzaEntity) = with(binding){
            pizzaTitle.text = listItem.name

            pricePizza.text = listItem.price.toInt().toString() + "₽"
            descriptionPizza.text = listItem.description




        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PizzaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pizza_items, parent, false)
        return  PizzaViewHolder(view)
    }

    override fun onBindViewHolder(holder: PizzaViewHolder, position: Int) {

        dataList.value?.let {
            val listItem = it[position]
            holder.bind(listItem)

            Picasso.get().load(listItem.imageUrl).into(holder.binding.pizzaImage)

            holder.binding.cardView.setOnClickListener{
                val bundle = Bundle()

                bundle.putString("name", listItem.name)
                bundle.putString("description", listItem.description)
                bundle.putString("price", listItem.price.toInt().toString())
                bundle.putString("imageUrl",listItem.imageUrl)

                val bottomSheet = BottomFragment()
                bottomSheet.arguments = bundle
                bottomSheet.show(fragmentManager, "tag")
            }
        }




    }

    override fun getItemCount() = dataList.value?.size?:0
}