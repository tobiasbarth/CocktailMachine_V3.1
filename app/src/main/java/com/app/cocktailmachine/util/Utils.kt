package com.app.cocktailmachine.util

import android.content.Context
import com.app.cocktailmachine.Constants
import com.app.cocktailmachine.model.Cocktail
import com.google.gson.Gson
import java.io.FileWriter
import java.io.IOException
import java.io.InputStream
import java.io.Writer

object Utils {


    fun loadCocktailData(context: Context): List<Cocktail> {
        //val cocktails: MutableList<Cocktail> = mutableListOf()
        //cocktails.add(Cocktail("test", Category.CREAMY, true, mapOf()))

        val gson = Gson()
        val cocktails: JsonInput = gson.fromJson( readFileFromAssets(context, Constants.dataFile), JsonInput::class.java)
        return cocktails.cocktails
    }

    class JsonInput(var cocktails: List<Cocktail>)

    fun readFileFromAssets(context: Context, fileName: String): String? {
        var jsonString: String
        try {
            val stream: InputStream = context.assets.open(fileName)
            val size: Int = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()
            jsonString = String(buffer)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }

        return jsonString
    }

}
