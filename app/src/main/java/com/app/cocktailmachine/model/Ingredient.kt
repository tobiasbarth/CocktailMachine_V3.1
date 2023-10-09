package com.app.cocktailmachine.model

import java.io.Serializable

class Ingredient(val grocery: Grocery, var amount: Int) : Serializable {

}