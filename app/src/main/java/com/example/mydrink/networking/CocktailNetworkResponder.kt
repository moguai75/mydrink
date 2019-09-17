package com.example.mydrink.networking

import com.example.mydrink.model.Drink
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CocktailNetworkResponder : Callback<Drink> {

    override fun onFailure(call: Call<Drink>, t: Throwable) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onResponse(call: Call<Drink>, response: Response<Drink>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}