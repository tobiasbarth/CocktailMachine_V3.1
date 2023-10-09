package com.app.cocktailmachine.connections

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.app.cocktailmachine.Constants
import com.app.cocktailmachine.model.Cocktail
import com.app.cocktailmachine.ui.main.SettingsFragment
import java.io.IOException
import java.io.OutputStream


object BluetoothConnection : BluetoothConnectionInterface {
    private var bluetoothSocket: BluetoothSocket? = null

    @RequiresApi(Build.VERSION_CODES.P)
    @Throws(IOException::class)
    override fun init(context: Context) {
        val blueAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
        if (blueAdapter != null) {
            if (blueAdapter.isEnabled) {
                val device: BluetoothDevice? =
                    blueAdapter.getRemoteDevice(Constants.macAddress)

                if (device != null && blueAdapter.bondedDevices.contains(device)) {
                    // Socket erstellen
                    var socket: BluetoothSocket? = null
                    try {
                        socket = device.createInsecureRfcommSocketToServiceRecord(Constants.uuid)
                        Log.d("Debug", "Created socket")
                    } catch (e: Exception) {
                        Log.e("error", "Could not create socket")
                        Toast.makeText(context, "Could not create socket", Toast.LENGTH_LONG).show()
                        //return
                    }
                    blueAdapter.cancelDiscovery()


                    if (socket != null) {
                        try {
                            socket.connect()
                            if (socket.isConnected) {
                                Log.d("Info", "Socket connected")
                                Toast.makeText(context, "Socket connected", Toast.LENGTH_LONG)
                                    .show()
                                bluetoothSocket = socket
                                var array = ArrayList<Byte>()
                                array.add((255).toByte())
                                array.add((253).toByte())
                                array.add((253).toByte())
                                val send = send(context, array.toByteArray())
                                if (send) {
                                    Toast.makeText(
                                        context,
                                        "send: " + array.toByteArray().contentToString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } catch (e: IOException) {
                            Log.e("Error", "Could not connect socket: $e")
                            socket.close()
                            Toast.makeText(context, "Could not connect socket", Toast.LENGTH_LONG)
                                .show()
                            //return
                        }
                    }

                } else {
                    Log.e("error", "Couldn´t connect to cocktail machine.")
                    Toast.makeText(
                        context,
                        "Please connect to cocktail machine",
                        Toast.LENGTH_LONG
                    ).show()
                }

            } else {
                Log.e("error", "Bluetooth is disabled.")
                Toast.makeText(context, "Bitte Bluetooth aktivieren.", Toast.LENGTH_LONG).show()
            }
        } else {
            Log.e("error", "No Bluetooth Adapter found.")
            Toast.makeText(context, "No Bluetooth Adapter found.", Toast.LENGTH_LONG).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    @Throws(IOException::class)
    override fun send(context: Context, array: ByteArray): Boolean {
        try {
            bluetoothSocket!!.outputStream!!.write(array)
        } catch (e: Exception) {
            init(context)
            try {
                bluetoothSocket!!.outputStream!!.write(array)
            } catch (e: Exception) {
                Log.e("error", "Could not send data.")
                //Toast.makeText(context, "Could not send data.", Toast.LENGTH_LONG).show()
                return false
            }
        }
        return true

    }

    override fun closeConnection(context: Context) {
        bluetoothSocket?.outputStream?.close()
        Toast.makeText(context, "Closed bluetooth connection.", Toast.LENGTH_LONG).show()
    }

}
