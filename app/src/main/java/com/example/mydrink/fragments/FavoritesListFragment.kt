package com.example.mydrink.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mydrink.R
import com.example.mydrink.adapter.FavoritListAdapter
import com.example.mydrink.fragments.HomeFragment.Companion.FILE_NAME
import com.example.mydrink.helper.Constants
import com.example.mydrink.model.Drink
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_favorites_list.*


class FavoritesListFragment : Fragment() {

    //Create an instance of the adapter that we want to use
    var adapter = FavoritListAdapter({favoritItem : Drink -> partItemClicked(favoritItem)})

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val gson = Gson()
        val data: MutableList<Drink> = arrayListOf()

        //Read favorites SharedPreferences
        val fav = view.context
            .getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
            .getString(Constants.KEY_FAVORITE, "No value")
        //Check if there are favorites
        if(fav != "No value") {
            val favoritArray = gson.fromJson(fav, Array<Drink>::class.java)
            if(favoritArray.isNotEmpty()) {

                val size = favoritArray.size
                for (i in 0 until size) {
                    // Get each drink
                    val favorit = favoritArray[i]
                    val drink = Drink()
                    drink.strDrink = favorit.strDrink
                    drink.strDrinkThumb = favorit.strDrinkThumb
                    drink.idDrink = favorit.idDrink
                    //Add every Data from SharedPreferences to data
                    data.add(drink)
                }
            }
            else
            {
                val drink = Drink()
                drink.strDrink = "No favorites yet!"
                drink.strDrinkThumb = "https://www.thecocktaildb.com/images/ingredients/ice-Small.png"
                drink.idDrink = "00000"
                //Add Data to data
                data.add(drink)
            }
        }
        //Set the data if there are no favorites yet
        else
        {
            val drink = Drink()
            drink.strDrink = "No favorites yet!"
            drink.strDrinkThumb = "https://www.thecocktaildb.com/images/ingredients/ice-Small.png"
            drink.idDrink = "00000"
            //Add Data to data
            data.add(drink)
        }
        //Inform the recycler view that it uses the adapter we created.
        recycler_view.adapter = adapter
        //The layout manager will tell the recycler view HOW to render the rows.
        recycler_view.layoutManager = LinearLayoutManager(view.context)
        //Set the data
        adapter.setData(data)

    }

    //Remove selected favorite
    private fun partItemClicked(favoritItem : Drink) {
        val gson = Gson()
        val drink = Drink()
        val favdata = ArrayList<Drink>()
        //Set data from favorite to delete
        drink.idDrink = favoritItem.idDrink
        //Read favorites SharedPreferences
        val fav = view?.context
            ?.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
            ?.getString(Constants.KEY_FAVORITE, "No value")
        val favoritArray = gson.fromJson(fav, Array<Drink>::class.java)
        val size = favoritArray.size
        for (i in 0 until size) {
            // Get each favorites in SharedPreferences
            val favorit = favoritArray[i]
            //Check for favorite to delete
            if (drink.idDrink != favorit.idDrink) {
                favdata.add(favorit)
            }
            //Skip favorite to delete
            else {

            }
        }
        //Check if it was the last favorit
        if(favdata.isNotEmpty()){
            //Write sharedPreferences if there any favorites
            view?.context
                ?.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
                ?.edit()
                ?.putString(Constants.KEY_FAVORITE, gson.toJson(favdata))
                ?.apply()

        }
        else
        {
            //If there no favorites left, write sharedPreferences and set data for display text no favorites
            view?.context
                ?.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
                ?.edit()
                ?.putString(Constants.KEY_FAVORITE, gson.toJson(favdata))
                ?.apply()
            drink.strDrink = "No favorites yet!"
            drink.strDrinkThumb = "https://www.thecocktaildb.com/images/ingredients/ice-Small.png"
            drink.idDrink = "00000"
            //Add Data to data
            favdata.add(drink)
        }
        //Show message success
        AlertDialog.Builder(context).setTitle(R.string.success)
            .setPositiveButton(R.string.dismiss) { dialog, _ -> dialog.dismiss() }
            .create().show()
        //Refresh view
        adapter.setData(favdata)
    }

}

