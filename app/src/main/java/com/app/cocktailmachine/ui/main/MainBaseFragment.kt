package com.app.cocktailmachine.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import com.app.cocktailmachine.ui.MainActivity

abstract class MainBaseFragment : Fragment() {

    lateinit var ACTIVITY: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ACTIVITY = context as MainActivity
    }
}