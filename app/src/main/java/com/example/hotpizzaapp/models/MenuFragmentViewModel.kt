package com.example.hotpizzaapp.models

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.hotpizzaapp.PizzaApp
import com.example.hotpizzaapp.data.remote.PizzaApi
import com.example.hotpizzaapp.data.remote.PizzaListItem
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MenuFragmentViewModel(app : Application): AndroidViewModel(app) {

    val pizzaList =  MutableLiveData<List<PizzaListItem>>()
    val pizzaListOpen = MutableLiveData(pizzaList.value)
    val pizzaApi = (app as PizzaApp).pizzaApi
    val compositeDisposable = CompositeDisposable()

    init {
        fetchPizzaList(pizzaApi)
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun fetchPizzaList(pizzaApi: PizzaApi) {

        compositeDisposable.add(pizzaApi.getAllPizza()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

                pizzaList.value = it
                pizzaListOpen.value = it

            },{
                //Maybe needs fix, IDN
                fetchPizzaList(pizzaApi)
            }))
    }

    fun searchPizza(value: String){
        val resList = mutableListOf<PizzaListItem>()
        value.let {
            pizzaList.value?.forEach { pizzaItem ->
                if(pizzaItem.name.lowercase().contains(it))
                    resList.add(pizzaItem)
            }
            pizzaListOpen.value = resList
        }
    }
}