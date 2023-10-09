package com.app.cocktailmachine.ui.singleCocktail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.app.cocktailmachine.R
import com.app.cocktailmachine.model.Cocktail
import com.app.cocktailmachine.ui.CocktailAdapter
import com.app.cocktailmachine.ui.SingleCocktailActivity

class FilteredCocktailsFragment : Fragment() {

    companion object {
        fun newInstance() = FilteredCocktailsFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val cocktailList: List<Cocktail> =
            activity?.intent?.getSerializableExtra("filtered_cocktails") as List<Cocktail>

        val filter: String = activity?.intent?.getStringExtra("filter")!!

        (activity as AppCompatActivity?)!!.supportActionBar!!.title = filter + " Cocktails"

        val root = inflater.inflate(R.layout.filtered_cocktails_fragment, container, false)

        val cocktailListView: ListView = root.findViewById(R.id.filtered_cocktails_list)

        cocktailListView.adapter =
            context?.let { CocktailAdapter(it, R.layout.cocktail_row, cocktailList) }
        cocktailListView.setOnItemClickListener { parent: AdapterView<*>, view, postition, id ->
            run {

                val intent = Intent(context, SingleCocktailActivity::class.java)
                intent.putExtra("cocktail", cocktailList[id.toInt()])
                startActivity(intent)
                //Toast.makeText(context, cocktailArray[id.toInt()], Toast.LENGTH_LONG).show()
            }
        }
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}