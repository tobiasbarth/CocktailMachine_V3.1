package com.app.cocktailmachine.ui.main
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.app.cocktailmachine.R
import com.app.cocktailmachine.ui.Counter


class StatsFragment : MainBaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.stats_fragment, container, false)
        val counter_val: TextView = root.findViewById(R.id.counter_val)
        val reset_btn: Button = root.findViewById(R.id.reset_stats)

        counter_val.setText(Counter.getValue(context!!).toString())

        reset_btn.setOnClickListener{
           Counter.reset(context!!)
            counter_val.setText(Counter.getValue(context!!).toString())
            Toast.makeText(context, "Statistiken zurückgesetzt!", Toast.LENGTH_LONG).show()
        }

        return root
    }
}