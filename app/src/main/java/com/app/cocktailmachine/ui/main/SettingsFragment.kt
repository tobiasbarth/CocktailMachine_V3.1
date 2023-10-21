package com.app.cocktailmachine.ui.main
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.app.cocktailmachine.Constants
import com.app.cocktailmachine.R
import com.app.cocktailmachine.ui.Counter
import kotlinx.android.synthetic.main.all_cocktails_fragment.*
import kotlinx.android.synthetic.main.settings_activity.*
import com.app.cocktailmachine.model.Grocery
import com.app.cocktailmachine.model.SharedPreferenceHelper
import com.app.cocktailmachine.ui.MainActivity

class SettingsFragment : MainBaseFragment() {

    private lateinit var tableLayout: TableLayout
    private lateinit var spinnerAmountPumps: Spinner


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val sharedPreferences = SharedPreferenceHelper.getInstance(requireContext())
        val root = inflater.inflate(R.layout.settings_activity, container, false)
        val macAdress: EditText = root.findViewById(R.id.mac_adresse_value)
        val macConfirm: Button = root.findViewById(R.id.confirm_mac)
        val ledButton: Button = root.findViewById(R.id.toggle_led)
        val cleanButton: Button = root.findViewById(R.id.cleaning)
        val prepareButton: Button = root.findViewById(R.id.prepare)
        val cocktailmachineButton: Button = root.findViewById(R.id.cocktailmachine_settings)
        val contentcocktailmachinesettings: LinearLayout = root.findViewById(R.id.cocktailmachine_settings_content)
        val ingredientsButton: Button = root.findViewById(R.id.Ingredients_settings)
        spinnerAmountPumps = root.findViewById(R.id.spinner_amountpumps)
        tableLayout = root.findViewById(R.id.pumptable)

        val valuesAmount = ArrayList<Int>()
        val maxNrPumps = 16
        for (num in 1..maxNrPumps){valuesAmount.add(num)}
        val adapterPumpAmount = ArrayAdapter<Int>(context!!, android.R.layout.simple_list_item_1, valuesAmount)
        spinnerAmountPumps.adapter = adapterPumpAmount

        val savedPumpNrValue = sharedPreferences.getInt("amountPumps")


        if (savedPumpNrValue != 0){
            val position = adapterPumpAmount.getPosition(savedPumpNrValue)
            if (position != -1)
            {
                spinnerAmountPumps.setSelection(position)
            }
        }

        spinnerAmountPumps.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            @SuppressLint("SetTextI18n")
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                sharedPreferences.saveInt("amountPumps", adapterPumpAmount.getItem(position)?:savedPumpNrValue)
                updateTable()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        // reset sharedPrefs
        /*
        for (grocery in Grocery.values())
        {
            sharedPreferences.saveInt(grocery.name + "Spot", 0)
            sharedPreferences.saveBool(grocery.name + "Available", false)
        }
        sharedPreferences.updateGrocerySpots()
        updateTable()

         */
        // end reset


        ledButton.setOnClickListener{
            if (context != null) {
                var arrayList = ArrayList<Byte>()
                // add 3 times 255 to signal end
                arrayList.add((255).toByte())
                arrayList.add((254).toByte())
                arrayList.add((254).toByte())

                var array = arrayList.toByteArray()
                val send = Constants.bluetoothConnection.send(context!!, array)
                if (send) {
                    Toast.makeText(context, "send: " + array.contentToString(), Toast.LENGTH_LONG).show()
                }
            }
        }

        cleanButton.setOnClickListener{
            if (context != null) {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Reinigungsprogramm")
                builder.setMessage("Heißes Wasser mit Spülmittel anschließen und starten")
                builder.setPositiveButton("ok") { dialog, arg1 ->
                    send_cleaning_data(40)
                    dialog.cancel()
                    builder.setMessage("Warten... Anschließend heißes Wasser anschließen und starten")
                    builder.setPositiveButton("ok") { dialog, arg1 ->
                        send_cleaning_data(40)
                        dialog.cancel()

                        builder.setMessage("Warten... Anschließend Leerlauf starten (nichts angeschlossen)")
                        builder.setPositiveButton("ok") { dialog, arg1 ->
                            send_cleaning_data(20)
                            dialog.cancel()
                        }
                        builder.setNegativeButton("abbrechen") { dialog, arg1 ->
                            dialog.cancel()
                        }
                        builder.show()
                    }
                    builder.setNegativeButton("abbrechen") { dialog, arg1 ->
                        dialog.cancel()
                    }
                    builder.show()
                }
                builder.setNegativeButton("abbrechen") { dialog, arg1 ->
                    dialog.cancel()
                }
                builder.show()
            }
        }

        prepareButton.setOnClickListener{
            if (context != null) {
                var arrayList = ArrayList<Byte>()
                // add 3 times 255 to signal end
                for (i in 1..16){
                    arrayList.add((i).toByte())
                    arrayList.add((3).toByte())
                    arrayList.add((3 * ((i-1) / 4)).toByte())
                }
                arrayList.add((255).toByte())
                arrayList.add((255).toByte())
                arrayList.add((255).toByte())

                var array = arrayList.toByteArray()
                val send = Constants.bluetoothConnection.send(context!!, array)
                if (send) {
                    Toast.makeText(context, "send: " + array.contentToString(), Toast.LENGTH_LONG).show()
                }

            }
        }

        cocktailmachineButton.setOnClickListener{
            if (context != null) {
                if(contentcocktailmachinesettings.isVisible == false)
                {
                    contentcocktailmachinesettings.setVisibility(View.VISIBLE)
                }
                else{
                    contentcocktailmachinesettings.setVisibility(View.GONE)
                }
            }
        }

        var addingredients = this.cocktailslist

        ingredientsButton.setOnClickListener{
            if (context != null) {
            }
        }
        return root
    }

    var cleaning_process_state = 0
    lateinit var progress: ProgressDialog

    fun updateTable(){
        tableLayout.removeAllViews()
        val sharedPreferences = SharedPreferenceHelper.getInstance(requireContext())

        val savedPumpNrValue = sharedPreferences.getInt("amountPumps")

        for (num in 1..savedPumpNrValue){
            val tableRow = TableRow(context)
            val textView = TextView(context)
            val spinnerView = Spinner(context)

            textView.text= "Pump $num"


            val values = ArrayList<String>()
            values.add("---")
            for (grocery in Grocery.values()){
                values.add(grocery.name)
            }

            val adapterGrocerySpinner : ArrayAdapter<String> = ArrayAdapter<String>(context!!, android.R.layout.simple_list_item_1, values)
            spinnerView.adapter = adapterGrocerySpinner

            
            // select saved Groceries spots
            for (grocery in Grocery.values())
            {
                if (grocery.spot == num.toByte()){
                    val position = adapterGrocerySpinner.getPosition(grocery.name)
                    spinnerView.setSelection(position)
                    break
                }
            }

            spinnerView.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                @SuppressLint("SetTextI18n")
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val newGrocery = adapterGrocerySpinner.getItem(position)
                    val oldGrocery = sharedPreferences.getString(num.toString() + "old")
                    
                    // forbid already used Groceries
                    if ((newGrocery != oldGrocery) and (sharedPreferences.getInt(newGrocery + "Spot") != 0))
                    {
                        Toast.makeText(context, "Grocery already used on Pump "+sharedPreferences.getInt(newGrocery + "Spot").toString(),  Toast.LENGTH_LONG).show()
                        spinnerView.setSelection(0)
                        return
                    }
                    
                    // disable old grocery
                    sharedPreferences.saveInt(oldGrocery + "Spot", 0)
                    sharedPreferences.saveBool(oldGrocery + "Available", false)
                    
                    // save new position and availability in shared Prefs
                    sharedPreferences.saveInt(newGrocery + "Spot", num)
                    sharedPreferences.saveBool(newGrocery + "Available", true)
                    if (newGrocery != null) {
                        sharedPreferences.saveString(oldGrocery, newGrocery)
                    }
                    // update Grocery.spots with data from sharedPref
                    sharedPreferences.updateGrocerySpots()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }

            tableRow.addView(textView)
            tableRow.addView(spinnerView)
            tableLayout.addView(tableRow)
        }

        // disable all groceries that are not visible
        for (grocery in Grocery.values())
        {
            if (grocery.spot > savedPumpNrValue){
                sharedPreferences.saveBool(grocery.name + "Available", false)
            }
        }
    }


    fun send_cleaning_data(duration: Int){
        var arrayList = ArrayList<Byte>()
        // add 3 times 255 to signal end
        for (i in 1..16) {
            arrayList.add((i).toByte())
            arrayList.add((duration).toByte())
            arrayList.add((duration * ((i - 1) / 4)).toByte())
        }
        arrayList.add((255).toByte())
        arrayList.add((255).toByte())
        arrayList.add((255).toByte())

        var array = arrayList.toByteArray()
        val send = Constants.bluetoothConnection.send(context!!, array)
        if (send) {
            Toast.makeText(
                context,
                "send: " + array.contentToString(),
                Toast.LENGTH_LONG
            ).show()
        }
    }
}