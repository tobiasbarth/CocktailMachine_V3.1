package com.app.cocktailmachine

import android.content.Context
import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.app.cocktailmachine.util.Utils

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    lateinit var instrumentationContext: Context

    @Before
    fun setup() {
        // Context of the app under test.
        instrumentationContext = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun validateData() {
        val data = Utils.loadCocktailData(instrumentationContext)
        for (cocktail in data) {
            assertNotNull(cocktail.name)
            Log.d("Checking Cocktail: ", cocktail.name)
            assert(cocktail.ingredients.isNotEmpty())
            assertNotNull(cocktail.category)
            assertNotNull(cocktail.image)
            assertNotNull(instrumentationContext.assets.open(Constants.imageFolder + cocktail.image))
            for( ingredient in cocktail.ingredients) {
                assertNotNull(ingredient.grocery)
                assertNotNull(ingredient.amount)
            }
        }
    }
}