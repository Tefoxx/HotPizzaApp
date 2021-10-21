package com.example.hotpizzaapp.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hotpizzaapp.data.PizzaDao
import com.example.hotpizzaapp.data.PizzaDatabase
import com.example.hotpizzaapp.data.PizzaEntity

class MenuFragmentViewModel: ViewModel() {

    val pizzaList = PizzaDatabase.pizzaDao.getAll()
    val pizzaListOpen = MutableLiveData(pizzaList)
}