package com.app.cocktailmachine.ui.main

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.app.cocktailmachine.R
import com.app.cocktailmachine.model.Cocktail
import com.app.cocktailmachine.ui.CocktailAdapter
import com.app.cocktailmachine.ui.SingleCocktailActivity


class SearchNameFragment : MainBaseFragment() {
    lateinit var filteredCocktailList: List<Cocktail>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.search_name_fragment, container, false)

        val cocktailListView: ListView = root.findViewById(R.id.searchlist)
        filteredCocktailList = ACTIVITY.cocktails

        val searchNameButton: Button = root.findViewById(R.id.namesearch)
        searchNameButton.background = resources.getDrawable(R.color.colorAccent)

        val button = root.findViewById<Button>(R.id.ingridientsearch)
        button.setOnClickListener{
            fragmentManager?.beginTransaction()?.replace(R.id.nav_host_fragment, SearchIngriedientFragment())?.commit()
            //fragmentManager?.beginTransaction()?.add(R.id.nav_host_fragment, SearchIngriedientFragment())?.commit()
        }

        var adapter = context?.let { CocktailAdapter(it, R.layout.cocktail_row,filteredCocktailList) }
        cocktailListView.adapter = adapter

        cocktailListView.setOnItemClickListener { parent: AdapterView<*>, view, postition, id ->
            run {

                val intent = Intent(context, SingleCocktailActivity::class.java)
                intent.putExtra("cocktail", filteredCocktailList[id.toInt()])
                startActivity(intent)
                //Toast.makeText(context, cocktailArray[id.toInt()], Toast.LENGTH_LONG).show()
            }
        }

        val inputField: EditText = root.findViewById(R.id.searchinput)
        inputField.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(inputString: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (inputString != null) {
                    filteredCocktailList = ACTIVITY.cocktails.filter{ x -> x.name.toLowerCase().contains(inputString.toString().toLowerCase())}
                    adapter?.update(filteredCocktailList)
                }
            }
        })


        return root
    }


}
