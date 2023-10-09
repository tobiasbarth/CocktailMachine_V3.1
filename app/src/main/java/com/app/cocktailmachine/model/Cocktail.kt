package com.app.cocktailmachine.model

import com.app.cocktailmachine.Constants
import java.io.Serializable
import kotlin.math.roundToInt

class Cocktail(val name: String, val image: String, val category: Category, val ingredients: List<Ingredient>) : Serializable{


    fun asByteArray(size: Size): ByteArray {
        var array = ArrayList<Byte>()
        ingredients.filter { x-> x.grocery.spot != 0.toByte() } .forEachIndexed { index, ingridient ->
            array.add(ingridient.grocery.spot)
            val outputAmount: Byte = (ingridient.amount * size.multiplicator * Constants.pumpFactor[ingridient.grocery.spot.toInt() - 1]).roundToInt().toByte()
            array.add(outputAmount)
            array.add((Constants.sleeptime * (index / 4)).toByte())
        }
        // add 3 times 255 to signal end
        array.add((255).toByte())
        array.add((255).toByte())
        array.add((255).toByte())

        return array.toByteArray()
    }

    fun getAdditionalGroceries(size: Size): List<Pair<String,Byte>> {
        return ingredients.filter{ x-> x.grocery.spot == 0.toByte() }.map { x -> Pair(x.grocery.label, (x.amount * Constants.generalMultiplicator * size.multiplicator).toByte()) }
    }



}