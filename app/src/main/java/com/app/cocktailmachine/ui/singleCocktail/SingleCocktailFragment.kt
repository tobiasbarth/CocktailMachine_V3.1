package com.app.cocktailmachine.ui.singleCocktail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.app.cocktailmachine.Constants
import com.app.cocktailmachine.R
import com.app.cocktailmachine.connections.MqttConnection
import com.app.cocktailmachine.model.Cocktail
import com.app.cocktailmachine.model.Grocery
import com.app.cocktailmachine.model.Ingredient
import com.app.cocktailmachine.model.Size
import com.app.cocktailmachine.ui.Counter
import kotlinx.android.synthetic.main.single_cocktail_fragment.*
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.json.JSONObject
import java.io.InputStream
import kotlin.math.roundToInt

class SingleCocktailFragment : Fragment() {

    var size = Size.MEDIUM
    val publish_data = JSONObject("""{"cocktail":"-", "groesse":"-"}""")
    companion object {
        fun newInstance() = SingleCocktailFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val cocktail: Cocktail = activity?.intent?.getSerializableExtra("cocktail") as Cocktail
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = cocktail.name

        val root = inflater.inflate(R.layout.single_cocktail_fragment, container, false)

        val title: TextView = root.findViewById(R.id.cocktail_title)
        title.text = cocktail.name

        val image: ImageView = root.findViewById(R.id.cocktail_image)

        val pourButton: Button = root.findViewById(R.id.pour_button)

        val radioGroup: RadioGroup = root.findViewById(R.id.size_group)


        val radioButtons = radioGroup.children
        radioButtons.forEach {
            it.setOnClickListener{
                when (it.id) {
                    R.id.small -> size = Size.SMALL
                    R.id.medium -> size = Size.MEDIUM
                    R.id.large -> size = Size.LARGE
                }
                refreshTable(root, cocktail, size)
            }
        }

        pourButton.setOnClickListener{

            if (context != null) {
                val array = cocktail.asByteArray(size)
                for (ingredient in cocktail.ingredients){
                    for (grocerys in Grocery.values())
                        {
                        if(ingredient.grocery.name == grocerys.toString() && grocerys.spot.toInt() == 0){
                            Toast.makeText(context, "zusätzlich "+grocerys.label+" hinzufügen", Toast.LENGTH_LONG).show()
                        }
                    }
                }

                val send = Constants.bluetoothConnection.send(context!!, array)
                if (send) {

                    //Toast.makeText(context, "send: " + array.contentToString(), Toast.LENGTH_LONG).show()
                    //Toast.makeText(context, "Add later: " + cocktail.getAdditionalGroceries(size).toString(), Toast.LENGTH_LONG).show()
                    Counter.increase(context!!)
                    if(Constants.mqttConnection.isConnected()) {
                        publish_data.put("cocktail", cocktail.name)
                        publish_data.put("groesse", size)
                        try {
                            Constants.mqttConnection.publishToTopic(publish_data.toString())
                        } catch (e: Exception) {
                            Log.d("Error", Log.getStackTraceString(e))
                        }
                    }

                }

            }
        }

        // get input stream
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


        refreshTable(root, cocktail, size)

        return root
    }

    fun refreshTable(root: View, cocktail: Cocktail, size : Size) {
        val table: TableLayout = root.findViewById(R.id.ingredients_table)
        table.removeAllViews()
        for(ingredient in cocktail.ingredients) {
            val row = TableRow(context)
            val ingredientView = TextView(context)
            val amountView = TextView(context)
            ingredientView.text = ingredient.grocery.label
            amountView.text = (ingredient.amount * size.multiplicator).roundToInt().toString() + "ml"

            ingredientView.width = 400
            amountView.width = 200
            row.addView(ingredientView)
            row.addView(amountView)

            table.addView(row)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}