package com.app.cocktailmachine.ui.main
import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
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

class SettingsFragment : MainBaseFragment() {

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