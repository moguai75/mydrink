package com.example.mydrink.networking

import com.example.mydrink.model.Drink
import com.example.mydrink.model.DrinkApiRequest
import retrofit2.Call
import retrofit2.http.GET

/**
 * This interface will represent our API.
 */
interface MeServiceInterface {

    @GET("api/json/v1/1/random.php")
    fun fetchRandomDrink(): Call<DrinkApiRequest>

}