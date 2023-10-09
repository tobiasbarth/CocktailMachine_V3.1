package com.app.cocktailmachine

import android.content.Context
import android.net.MacAddress
import android.os.Build
import androidx.annotation.RequiresApi
import com.app.cocktailmachine.connections.*
import java.util.*

object Constants {

    // 1.0 = 100ml mit 100 als Daten
    val pumpFactor: Array<Double> = arrayOf(0.55,0.55,0.55,
        0.9,0.75,0.9,0.75,0.75,
        1.4,1.4,1.4,1.4,1.4,1.4,
        0.75,0.9)
    val imageFolder =  "images/"
    val dataFile = "normalized.json"
    // to transform percentage to values
    val generalMultiplicator = 1
    // waittime for next grocery
    val sleeptime = 30
    @RequiresApi(Build.VERSION_CODES.P)
    val macAddress: String = "00:14:03:06:07:D8"
    var uuid: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    val bluetoothConnection: BluetoothConnectionInterface = BluetoothConnection
    val mqttConnection: MQTTConnectionInterface = MqttConnection
}