package com.example.hotpizzaapp.data.remote

import io.reactivex.Single
import retrofit2.http.GET

interface PizzaApi {
    @GET("pizza")
    fun getAllPizza(): Single<List<PizzaListItem>>
}