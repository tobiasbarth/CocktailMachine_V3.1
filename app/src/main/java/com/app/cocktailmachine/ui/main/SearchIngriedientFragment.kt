package com.app.cocktailmachine.ui.main

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.app.cocktailmachine.R
import com.app.cocktailmachine.model.Cocktail
import com.app.cocktailmachine.model.Grocery
import com.app.cocktailmachine.ui.CocktailAdapter
import com.app.cocktailmachine.ui.SingleCocktailActivity


class SearchIngriedientFragment : MainBaseFragment() {
    lateinit var filteredCocktailList: List<Cocktail>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.search_ingridient_fragment, container, false)

        val cocktailListView: ListView = root.findViewById(R.id.searchlist)
        filteredCocktailList = ACTIVITY.cocktails


        val searchNameButton: Button = root.findViewById(R.id.ingridientsearch)
        searchNameButton.background = resources.getDrawable(R.color.colorAccent)

        val button = root.findViewById<Button>(R.id.namesearch)
        button.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.nav_host_fragment, SearchNameFragment())?.commit()
            //fragmentManager?.beginTransaction()?.add(R.id.nav_host_fragment, SearchNameFragment())?.commit()
        }

        var adapter =
            context?.let { CocktailAdapter(it, R.layout.cocktail_row, filteredCocktailList) }
        cocktailListView.adapter = adapter

        cocktailListView.setOnItemClickListener { parent: AdapterView<*>, view, postition, id ->
            run {

                val intent = Intent(context, SingleCocktailActivity::class.java)
                intent.putExtra("cocktail", filteredCocktailList[id.toInt()])
                startActivity(intent)
                //Toast.makeText(context, cocktailArray[id.toInt()], Toast.LENGTH_LONG).show()
            }
        }


        var items = Grocery.values().sortedBy { x -> x.label }
        var selectedItems = ArrayList<Grocery>()
        val builder = AlertDialog.Builder(context)
        var checkedItems = BooleanArray(items.size)
        // Set the dialog title
        builder.setTitle("Ingridients")
            // Specify the list array, the items to be selected by default (null for none),
            // and the listener through which to receive callbacks when items are selected
            .setMultiChoiceItems(items.map { x -> x.label }.toTypedArray(), checkedItems, { dialogInterface: DialogInterface, i: Int, b: Boolean -> })
            // Set the action buttons
            .setPositiveButton("Ok") { dialog, id ->
                // User clicked OK, so save the selectedItems results somewhere
                // or return them to the component that opened the dialog
                selectedItems.clear()
                for (i in checkedItems.indices) {
                    if (checkedItems[i]) {
                        selectedItems.add(items[i])
                    }
                }

                filteredCocktailList = ACTIVITY.cocktails.filter{cocktail ->
                    var returnvalue = true
                    for (item in selectedItems) {
                        var hasItem: Boolean = false
                        cocktail.ingredients.forEach{ingridient -> if(ingridient.grocery == item) { hasItem = true } }
                        if (!hasItem) {
                            returnvalue = false
                        }
                    }
                    returnvalue
                }
                adapter?.update(filteredCocktailList)


                dialog.dismiss()
            }


        val dialog = builder.create()


        val dialogbutton = root.findViewById<Button>(R.id.openDialog)
        dialogbutton.setOnClickListener {
            dialog.show()
            //fragmentManager?.beginTransaction()?.add(R.id.nav_host_fragment, SearchNameFragment())?.commit()
        }




        return root
    }


}