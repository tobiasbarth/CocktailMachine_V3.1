package com.app.cocktailmachine.connections

import android.content.Context
import com.app.cocktailmachine.model.Cocktail
import java.io.IOException

interface BluetoothConnectionInterface {

    @Throws(IOException::class)
    fun init(context: Context)

    @Throws(IOException::class)
    fun send(context: Context, array: ByteArray): Boolean

    fun closeConnection(context: Context)
}