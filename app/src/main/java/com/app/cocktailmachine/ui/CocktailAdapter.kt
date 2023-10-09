package com.app.cocktailmachine.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.app.cocktailmachine.Constants
import com.app.cocktailmachine.R
import com.app.cocktailmachine.model.Cocktail
import java.io.InputStream


class CocktailAdapter(var mCtx: Context, var resources:Int, var cocktailList: List<Cocktail>) : ArrayAdapter<Cocktail>(mCtx, resources, cocktailList) {


    override fun getView(position: Int, contentView: View?, parent: ViewGroup): View {

        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(resources, null)
        val image: ImageView = view.findViewById(R.id.cocktail_row_image)
        val cocktailText: TextView = view.findViewById(R.id.cocktail_row_text)

        var cocktail: Cocktail = cocktailList[position]

        try {
            val ims: InputStream? = context?.assets?.open(Constants.imageFolder + cocktail.image)
            // load image as Drawable
            ims.let {
                val drawable: Drawable = Drawable.createFromStream(ims, null)
                // set image to ImageView
                image.setImageDrawable(drawable)
            }
        } catch (e: Exception) {
            Log.d("Error", Log.getStackTraceString(e))
        }

        cocktailText.text = cocktail.name

        return view

    }

    override fun getItem(position: Int): Cocktail? {
        return cocktailList[position]
    }

    override fun getCount(): Int {
        return cocktailList.size
    }



    fun update(updatedList: List<Cocktail> ) {
        //var items: ArrayList<Cocktail> =  ArrayList<Cocktail>()
        //items.addAll(updatedList)
        cocktailList = updatedList
        notifyDataSetChanged()
    }

}