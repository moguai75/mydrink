package com.example.mydrink.networking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Wrapper class to create the retrofit
 * instance which we can use for the network.
 */
class MyOwnRetrofitClient() {
    //The effective api client to use.
    val apiClient: MeServiceInterface

    init {
        //Create the retrofit instance
        val retrofitClient = Retrofit.Builder()
                //We set a base url, so that every call will use it as prefix
            .baseUrl("https://www.thecocktaildb.com/")
                    //Convert every http body to the selected type (in this case Json)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        //Now use the interface to bind it with the retrofit instance.
        apiClient = retrofitClient.create(MeServiceInterface::class.java)

    }

}