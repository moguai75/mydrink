package com.example.mydrink.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.example.mydrink.R
import com.example.mydrink.model.Drink
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.item_drink.view.*

/**
 * The user adapter is responsible for matching the data with the placeholder class.
 * It is also responsible for binding and recreating.
 *
 * REMEMBER: Call a notify function like [notifyDataSetChanged] to inform the Adapter class
 * that we have changed the data, else it will not rerender.
 */
class FavoritListAdapter(val clickListener: (Drink) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private var data: List<Drink> = arrayListOf()

    /**
     * Creates the placeholder for every data item that will be used.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //Get the class responsible for creating a code representation of the xml file.
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_drink, parent, false)
        //Now create the placeholder object with our layout
        return DrinkViewHolder(view)
    }

    /**
     * Return the size of data items that we currently want to display.
     */
    override fun getItemCount(): Int {
        return data.size
    }

    /**
     * Call the function for binding the data and the layout
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is DrinkViewHolder) {
            holder.bind(data[position], clickListener)
        }
    }

    /**
     * Set the new data set to this adapter
     */
    fun setData(newData: List<Drink>) {
        data = newData
        notifyDataSetChanged()
    }


    /**
     * Create our own class for binding the data with the row layout
     */
    class DrinkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(drink: Drink, clickListener: (Drink) -> Unit) {

            //Load the image to the corresponding image view
            Picasso.get().load(drink.strDrinkThumb)
                .transform(RoundedCornersTransformation(90, 0))
                .into(itemView.imageView)
            //Set the drink data and clickListener
            itemView.title.text = drink.strDrink
            itemView.hidden_id.text = drink.idDrink
            //set clickListener to the button
            itemView.delfavbutton.setOnClickListener{ clickListener(drink)}
            //If no favorites hide button to delete
            if(drink.idDrink == "00000"){
                itemView.delfavbutton.isInvisible = true
            }
        }
    }
}