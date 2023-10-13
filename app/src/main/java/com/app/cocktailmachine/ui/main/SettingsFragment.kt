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

class SettingsFragment : MainBaseFragment() {

    private lateinit var sharedPref: SharedPreferences
    private lateinit var tableLayout: TableLayout
    private lateinit var spinnerAmountPumps: Spinner

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

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
        sharedPref = context!!.getSharedPreferences("myprefs", Context.MODE_PRIVATE)

        val valuesAmount = ArrayList<Int>()
        val maxNrPumps = 16
        for (num in 1..maxNrPumps){valuesAmount.add(num)}
        val adapterPumpAmount = ArrayAdapter<Int>(context!!, android.R.layout.simple_list_item_1, valuesAmount)
        spinnerAmountPumps.adapter = adapterPumpAmount

        val savedPumpNrValue = sharedPref.getInt("selectedValue", 0)
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
                with(sharedPref.edit())
                {
                    putInt("selectedValue", adapterPumpAmount.getItem(position)?:savedPumpNrValue)
                    apply()
                    updateTable()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

//        updateTable()

//        val spinner: Spinner = root.findViewById(R.id.spinner)
//        val values = ArrayList<String>()
//        for (grocery in Grocery.values()){ values.add(grocery.name)}
//        spinner.adapter = ArrayAdapter<String>(context!!, android.R.layout.simple_list_item_1, values)

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
        for (num in 1..sharedPref.getInt("selectedValue", 1)){
            val tableRow = TableRow(context)
            val textView = TextView(context)
            val buttonView = CheckBox(context)
            val spinnerView = Spinner(context)
            val values = ArrayList<String>()
            for (grocery in Grocery.values()){ values.add(grocery.name)}
            spinnerView.adapter = ArrayAdapter<String>(context!!, android.R.layout.simple_list_item_1, values)
            textView.text= "Pump $num"
            tableRow.addView(textView)
            tableRow.addView(buttonView)
            tableRow.addView(spinnerView)
            tableLayout.addView(tableRow)
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