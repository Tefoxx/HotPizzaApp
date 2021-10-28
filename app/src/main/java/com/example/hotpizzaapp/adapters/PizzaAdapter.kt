package com.example.hotpizzaapp.adapters

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.hotpizzaapp.BottomFragment
import com.example.hotpizzaapp.R
import com.example.hotpizzaapp.data.BundleKeys
import com.example.hotpizzaapp.data.remote.PizzaListItem
import com.example.hotpizzaapp.databinding.PizzaItemsBinding
import com.squareup.picasso.Picasso
import io.reactivex.subjects.BehaviorSubject


class PizzaAdapter(private val fragmentManager: FragmentManager) : RecyclerView.Adapter<PizzaAdapter.PizzaViewHolder>(){

    private var dataList: List<PizzaListItem>? =  emptyList()

    class PizzaViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val binding = PizzaItemsBinding.bind(view)

        @SuppressLint("SetTextI18n")
        fun bind(listItem: PizzaListItem, fragmentManager: FragmentManager) = with(binding){
            pizzaTitle.text = listItem.name

            pricePizza.text = listItem.price.toInt().toString() + "₽"
            descriptionPizza.text = listItem.description

            cardView.setOnClickListener{
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
                bottomSheet.show(fragmentManager, "tag")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PizzaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pizza_items, parent, false)
        return  PizzaViewHolder(view)
    }

    override fun onBindViewHolder(holder: PizzaViewHolder, position: Int) {

        dataList?.let {
            val listItem = it[position]
            holder.bind(listItem, fragmentManager)
            Picasso.get().load(listItem.imgList.first()).into(holder.binding.pizzaImage)

        }
    }

    override fun getItemCount() = dataList?.size?:0

    fun changeData(items: List<PizzaListItem>?){
        dataList = items
    }
}