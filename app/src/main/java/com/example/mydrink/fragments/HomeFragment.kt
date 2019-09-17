package com.example.mydrink.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mydrink.R
import com.example.mydrink.helper.Constants
import com.example.mydrink.model.Drink
import com.example.mydrink.model.DrinkApiRequest
import com.example.mydrink.networking.MyOwnRetrofitClient
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response




class HomeFragment: Fragment() {

    companion object{
        const val  FILE_NAME = "Favorites"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        // Call a drink
        val client = MyOwnRetrofitClient()

        client.apiClient.fetchRandomDrink()
            .enqueue(object : Callback<DrinkApiRequest> {
                override fun onFailure(call: Call<DrinkApiRequest>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<DrinkApiRequest>,
                    response: Response<DrinkApiRequest>
                ) {
                    response.body()?.drinks?.get(0)?.apply {
                        Picasso.get().load(this.strDrinkThumb)
                            .transform(RoundedCornersTransformation(90, 0))
                            .into(drink_img)

                        drink_name.text = this.strDrink
                        ingredient_1.text = this.strIngredient1
                        ingredient_2.text = this.strIngredient2
                        ingredient_3.text = this.strIngredient3
                        ingredient_4.text = this.strIngredient4
                        ingredient_5.text = this.strIngredient5
                        ingredient_6.text = this.strIngredient6
                        ingredient_7.text = this.strIngredient7
                        ingredient_8.text = this.strIngredient8
                        ingredient_9.text = this.strIngredient9
                        ingredient_10.text = this.strIngredient10
                        ingredient_11.text = this.strIngredient11
                        ingredient_12.text = this.strIngredient12
                        ingredient_13.text = this.strIngredient13
                        ingredient_14.text = this.strIngredient14
                        ingredient_15.text = this.strIngredient15

                        instructions.text = this.strInstructions

                        category.text = this.strCategory

                        alcoholic.text = this.strAlcoholic
                        glass.text = this.strGlass
                        hidden_url.text = this.strDrinkThumb
                        hidden_id.text = this.idDrink


                    }
                }

            })

    }

    //Set clickListener to favButton
    private fun initViews() {
        favButton.setOnClickListener(clickListener())

    }

    private fun clickListener(): View.OnClickListener {
        return View.OnClickListener {
            val gson = Gson()
            val drink = Drink()
            val favdata = ArrayList<Drink>()
            //Set data from drink shown on screen
            drink.strDrinkThumb = hidden_url.text.toString()
            drink.strDrink = drink_name.text.toString()
            drink.idDrink = hidden_id.text.toString()
            //Read favorites SharedPreferences
            val fav = view?.context
                ?.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
                ?.getString(Constants.KEY_FAVORITE, "No value")
            //Check if there are favorites
            if(fav != "No value") {
                val favoritArray = gson.fromJson(fav, Array<Drink>::class.java)
                val size = favoritArray.size
                for (i in 0 until size) {
                    // Get each favorites in SharedPreferences
                    val favorit = favoritArray[i]
                    //Check if favorite already exist
                    if(drink.idDrink != favorit.idDrink){
                    favdata.add(favorit)}
                    //When favorit already exist, show message and go back OnclickListener
                    else{
                        AlertDialog.Builder(it.context).setTitle(R.string.nosuccess)
                            .setMessage(R.string.nosuccess_body)
                            .setPositiveButton(R.string.dismiss) { dialog, _ -> dialog.dismiss() }
                            .create().show()
                        return@OnClickListener
                    }}
                    //Add drink shown on screen
                    favdata.add(drink)
                //Write
                view?.context
                    ?.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
                    ?.edit()
                    ?.putString(Constants.KEY_FAVORITE, gson.toJson(favdata))
                    ?.apply()
                //Show message success
                AlertDialog.Builder(it.context).setTitle(R.string.success)
                    .setPositiveButton(R.string.dismiss) { dialog, _ -> dialog.dismiss() }
                    .create().show()
             }
            else
            {
                //Set data if no favorites yet
                favdata.add(drink)
                //Write
                view?.context
                    ?.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
                    ?.edit()
                    ?.putString(Constants.KEY_FAVORITE, gson.toJson(favdata))
                    ?.apply()
                //Show message success
                AlertDialog.Builder(it.context).setTitle(R.string.success)
                    .setPositiveButton(R.string.dismiss) { dialog, _ -> dialog.dismiss() }
                    .create().show()
            }


            }
        }
    }
