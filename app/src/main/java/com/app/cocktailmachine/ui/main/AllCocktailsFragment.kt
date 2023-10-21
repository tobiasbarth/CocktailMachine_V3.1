package com.app.cocktailmachine.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import com.app.cocktailmachine.R
import com.app.cocktailmachine.model.Cocktail
import com.app.cocktailmachine.model.Grocery
import com.app.cocktailmachine.ui.CocktailAdapter
import com.app.cocktailmachine.ui.SingleCocktailActivity
import com.app.cocktailmachine.util.Utils
import java.io.File


class AllCocktailsFragment : MainBaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.all_cocktails_fragment, container, false)

        val cocktailListView: ListView = root.findViewById(R.id.cocktailslist)

        //val cocktailList: List<Cocktail> = ACTIVITY.cocktails
        val mutableCockailList: MutableList<Cocktail> = ACTIVITY.cocktails.toMutableList()
        mutableCockailList.removeAll { cocktail ->cocktail.ingredients.any { !it.grocery.available } }
        val cocktailList: List<Cocktail> = mutableCockailList.toList()

        cocktailListView.adapter =
            context?.let { CocktailAdapter(it, R.layout.cocktail_row, cocktailList) }
        cocktailListView.setOnItemClickListener { _: AdapterView<*>, _, _, id ->
            run {

                val intent = Intent(context, SingleCocktailActivity::class.java)
                intent.putExtra("cocktail", cocktailList[id.toInt()])
                startActivity(intent)
                //Toast.makeText(context, cocktailArray[id.toInt()], Toast.LENGTH_LONG).show()
            }
        }

        return root
    }


}