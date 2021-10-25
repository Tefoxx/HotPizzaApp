package com.example.hotpizzaapp.models

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hotpizzaapp.PizzaApp
import com.example.hotpizzaapp.data.PizzaEntity
import com.example.hotpizzaapp.data.remote.PizzaApi
import com.example.hotpizzaapp.data.remote.PizzaListItem
import com.example.hotpizzaapp.data.remote.PizzaListResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MenuFragmentViewModel(app : Application): AndroidViewModel(app) {

    val pizzaList =  MutableLiveData<List<PizzaListItem>>()

    val pizzaListOpen = MutableLiveData(pizzaList.value)

    val pizzaApi = (app as PizzaApp).pizzaApi

    private val compositeDisposable = CompositeDisposable()

    fun fetchPizzaList(pizzaApi: PizzaApi){
        compositeDisposable.add(pizzaApi.getAllPizza()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                    pizzaList.value = it
                    pizzaListOpen.value = pizzaList.value
            },{

            }))
    }
}