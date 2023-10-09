package com.app.cocktailmachine.ui.main
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import com.app.cocktailmachine.ui.FilteredCocktailsActivity
import com.app.cocktailmachine.R
import com.app.cocktailmachine.model.Category
import java.io.Serializable


class CategoriesFragment : MainBaseFragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.categories_fragment, container, false)

        val categoryListView : ListView = root.findViewById(R.id.categorielist)
        val categoryList =  Category.values()
        categoryListView.adapter = context?.let { ArrayAdapter<String>(it, android.R.layout.simple_list_item_1, categoryList.map { x -> x.value }) }
        categoryListView.setOnItemClickListener {
                parent, view, postition, id-> run {

            val intent = Intent(context, FilteredCocktailsActivity::class.java)
            val category: Category = categoryList[id.toInt()]
            intent.putExtra("filtered_cocktails", ACTIVITY.cocktails.filter { x -> x.category == category } as Serializable)
            intent.putExtra("filter", category.value)
            startActivity(intent)
            //Toast.makeText(context, cocktailArray[id.toInt()], Toast.LENGTH_LONG).show()
        }
        }

        return root
    }
}