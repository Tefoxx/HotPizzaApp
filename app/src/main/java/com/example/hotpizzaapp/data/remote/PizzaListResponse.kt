package com.example.hotpizzaapp.data.remote

import com.google.gson.annotations.SerializedName

data class PizzaListItem(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("price")
    val price: Float,

    @SerializedName("description")
    val description: String,

    @SerializedName("imageUrls")
    val imgList: List<String>
)
