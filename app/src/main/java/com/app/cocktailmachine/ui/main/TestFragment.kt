package com.app.cocktailmachine.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import com.app.cocktailmachine.Constants
import com.app.cocktailmachine.R
import com.app.cocktailmachine.model.Size
import kotlinx.android.synthetic.main.settings_activity.*
import kotlin.math.roundToInt

class TestFragment : MainBaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        var pump: Int = 1
        val root = inflater.inflate(R.layout.test_fragment, container, false)
        val inputField: EditText = root.findViewById(R.id.pumpinput)
        inputField.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(inputString: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (inputString != null && inputString.isNotEmpty()) {
                    pump = inputString.toString().toInt()
                }
            }
        })


        val pourButton: Button = root.findViewById(R.id.test_button)


        pourButton.setOnClickListener{

            if (context != null) {
                var arrayList = ArrayList<Byte>()
                // add 3 times 255 to signal end
                arrayList.add((pump).toByte())
                arrayList.add((100 * Constants.pumpFactor[pump - 1]).roundToInt().toByte())
                arrayList.add((0).toByte())
                arrayList.add((255).toByte())
                arrayList.add((255).toByte())
                arrayList.add((255).toByte())

                var array = arrayList.toByteArray()
                val send = Constants.bluetoothConnection.send(context!!, array)
                if (send) {
                    // Toast.makeText(context, "send: " + array.contentToString(), Toast.LENGTH_LONG).show()
                }

            }

        }


        return root
    }
}