package com.app.cocktailmachine.connections

import android.content.Context
import android.widget.Toast

object BluetoothConnectionMock : BluetoothConnectionInterface {

    override fun init(con: Context) {
        Thread.sleep(5000)
        Toast.makeText(con, "init Connection", Toast.LENGTH_SHORT).show()
    }

    override fun send(con: Context, array: ByteArray): Boolean {
        return true
    }

    override fun closeConnection(context: Context) {
        Toast.makeText(context, "close Connection", Toast.LENGTH_SHORT).show()
    }
}