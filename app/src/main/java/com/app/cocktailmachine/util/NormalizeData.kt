package com.app.cocktailmachine.util

import com.app.cocktailmachine.model.Cocktail
import com.google.gson.Gson
import java.io.*


fun main() {

    val gson = Gson()
    val reader: Reader = FileReader("app/src/main/assets/data.json")
    val data: Utils.JsonInput = gson.fromJson(reader, Utils.JsonInput::class.java)
    val file = File("app/src/main/assets/normalized.json")

    data.cocktails = data.cocktails.sortedBy { x -> x.name}
    for (cocktail in data.cocktails) {
        var sum: Int = 0
        var percentagesum: Int = 0
        cocktail.ingredients.forEach {
            sum += it.amount
        }
        cocktail.ingredients.forEach {
            it.amount = (it.amount * 100) / sum
            percentagesum += it.amount
        }

        cocktail.ingredients.forEach {
            if (percentagesum < 100) {
                it.amount = it.amount + 1
                percentagesum++
            }
        }

    }
    FileOutputStream(file).bufferedWriter().use { writer ->
        writer.write(gson.toJson(data))
        writer.newLine()
    }
}



