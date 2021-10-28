package com.example.hotpizzaapp.models

import android.app.Application
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
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.schedulers.Schedulers

class MenuFragmentViewModel(app : Application): AndroidViewModel(app) {

    //Не до конца понимаю, зачем убирать LiveData, если RxJava и LiveData дополняют себя
    //Может просто лучше объяснить мне нужно

    //val pizzaList =  MutableLiveData<List<PizzaListItem>>()

    val pizzaList by lazy {
        val processor = BehaviorProcessor.create<List<PizzaListItem>>()
        processor.offer(listOf())
        processor
    }

    val pizzaListOpen by lazy {
        val processor = BehaviorProcessor.create<List<PizzaListItem>>()
        processor.offer(listOf())
        processor
    }


    val pizzaApi = (app as PizzaApp).pizzaApi

    val compositeDisposable = CompositeDisposable()


    fun fetchPizzaList(pizzaApi: PizzaApi) {

        //В любом случае, всё сюда "запихать" можно, тут и данные пришли с API и все данные получал не в главном потоке
        //Потом в LiveData присвоил, и потом уже ими пользуешься так, как хочешь
        compositeDisposable.add(pizzaApi.getAllPizza()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

                    pizzaList.offer(it)
                    pizzaListOpen.offer(it)

            },{
                //На "выходной" неделе сделаю кнопку или ещё что-то, чтобы App не перезагружать
                Toast.makeText(getApplication(),
                    "Убедитесь, что у вас есть доступ к интернету и перезапустите приложение",
                    Toast.LENGTH_LONG).show()
            }))
    }

    fun searchPizza(value: String){
        val resList = mutableListOf<PizzaListItem>()
        value.let {
            pizzaList.value?.forEach { pizzaItem ->
                if(pizzaItem.name.lowercase().contains(it))
                    resList.add(pizzaItem)
            }
           pizzaListOpen.offer(resList)
        }
    }

}