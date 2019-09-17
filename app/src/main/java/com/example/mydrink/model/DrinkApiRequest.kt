package com.example.mydrink.model

import com.google.gson.annotations.SerializedName

data class DrinkApiRequest(
    @SerializedName("drinks")
    val drinks: List<Drink>
)