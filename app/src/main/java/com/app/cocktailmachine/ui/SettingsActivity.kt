package com.app.cocktailmachine.ui

import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.app.cocktailmachine.Constants
import com.app.cocktailmachine.R
import kotlinx.android.synthetic.main.all_cocktails_fragment.*
import kotlinx.android.synthetic.main.settings_activity.*
//
//
///*
//* !!!!!!!!!!!!!!!! SettingsActivity replaced with SettingsFragment !!!!!!!!!!!!!!!!!!!!!!!
//*
//* */
//
//class SettingsActivity : AppCompatActivity() {
//    var cleaning_process_state = 0
//    lateinit var progress: ProgressDialog
//
//    //get the spinner from the xml.
//
//
//
//
//
//    fun send_cleaning_data(duration: Int){
//        var arrayList = ArrayList<Byte>()
//        // add 3 times 255 to signal end
//        for (i in 1..16) {
//            arrayList.add((i).toByte())
//            arrayList.add((duration).toByte())
//            arrayList.add((duration * ((i - 1) / 4)).toByte())
//        }
//        arrayList.add((255).toByte())
//        arrayList.add((255).toByte())
//        arrayList.add((255).toByte())
//
//        var array = arrayList.toByteArray()
//        val send = Constants.bluetoothConnection.send(applicationContext!!, array)
//        if (send) {
//            Toast.makeText(
//                applicationContext,
//                "send: " + array.contentToString(),
//                Toast.LENGTH_LONG
//            ).show()
//        }
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        // Inflate the layout for this fragment
//
//        setContentView(R.layout.settings_activity)
//
//        val ledButton: Button = findViewById(R.id.toggle_led)
//        val cleanButton: Button = findViewById(R.id.cleaning)
//        val prepareButton: Button = findViewById(R.id.prepare)
//        val cocktailmachineButton: Button = findViewById(R.id.cocktailmachine_settings)
//        val contentcocktailmachinesettings: LinearLayout = findViewById(R.id.cocktailmachine_settings_content)
//        val ingredientsButton: Button = findViewById(R.id.Ingredients_settings)
//
//
//        val spinner: Spinner = findViewById(R.id.slot1_spinner)
//        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ArrayList<String>())
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        spinner.adapter = adapter
//        val data = arrayOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")
//        adapter.addAll(*data)
//        adapter.notifyDataSetChanged()
//
//
//
//        ledButton.setOnClickListener{
//            if (applicationContext != null) {
//                var arrayList = ArrayList<Byte>()
//                // add 3 times 255 to signal end
//                arrayList.add((255).toByte())
//                arrayList.add((254).toByte())
//                arrayList.add((254).toByte())
//
//                var array = arrayList.toByteArray()
//                val send = Constants.bluetoothConnection.send(applicationContext!!, array)
//                if (send) {
//                    Toast.makeText(applicationContext, "send: " + array.contentToString(), Toast.LENGTH_LONG).show()
//                }
//            }
//        }
//
//        cleanButton.setOnClickListener{
//            if (applicationContext != null) {
//                val builder = AlertDialog.Builder(this)
//                builder.setTitle("Reinigungsprogramm")
//                builder.setMessage("Heißes Wasser mit Spülmittel anschließen und starten")
//                builder.setPositiveButton("ok") { dialog, arg1 ->
//                    send_cleaning_data(40)
//                    dialog.cancel()
//                    builder.setMessage("Warten... Anschließend heißes Wasser anschließen und starten")
//                    builder.setPositiveButton("ok") { dialog, arg1 ->
//                        send_cleaning_data(40)
//                        dialog.cancel()
//
//                        builder.setMessage("Warten... Anschließend Leerlauf starten (nichts angeschlossen)")
//                        builder.setPositiveButton("ok") { dialog, arg1 ->
//                            send_cleaning_data(20)
//                            dialog.cancel()
//                        }
//                        builder.setNegativeButton("abbrechen") { dialog, arg1 ->
//                            dialog.cancel()
//                        }
//                        builder.show()
//                    }
//                    builder.setNegativeButton("abbrechen") { dialog, arg1 ->
//                        dialog.cancel()
//                    }
//                    builder.show()
//                }
//                builder.setNegativeButton("abbrechen") { dialog, arg1 ->
//                    dialog.cancel()
//                }
//                builder.show()
//            }
//        }
//
//        prepareButton.setOnClickListener{
//            if (applicationContext != null) {
//                var arrayList = ArrayList<Byte>()
//                // add 3 times 255 to signal end
//                for (i in 1..16){
//                    arrayList.add((i).toByte())
//                    arrayList.add((3).toByte())
//                    arrayList.add((3 * ((i-1) / 4)).toByte())
//                }
//                arrayList.add((255).toByte())
//                arrayList.add((255).toByte())
//                arrayList.add((255).toByte())
//
//                var array = arrayList.toByteArray()
//                val send = Constants.bluetoothConnection.send(applicationContext!!, array)
//                if (send) {
//                    Toast.makeText(applicationContext, "send: " + array.contentToString(), Toast.LENGTH_LONG).show()
//                }
//            }
//        }
//
//        cocktailmachine_settings.setOnClickListener{
//            if (applicationContext != null) {
//                if(contentcocktailmachinesettings.isVisible == false)
//                {
//                    contentcocktailmachinesettings.setVisibility(View.VISIBLE)
//                }
//                else{
//                    contentcocktailmachinesettings.setVisibility(View.GONE)
//                }
//            }
//        }
//
//        var addingredients = this.cocktailslist
//
//        ingredientsButton.setOnClickListener{
//            if (applicationContext != null) {
//            }
//        }
//
//
//
//    }
//}
//
