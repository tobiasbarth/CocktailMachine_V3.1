package com.app.cocktailmachine.connections

import android.content.Context
import android.util.Log
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import java.io.IOException

interface MQTTConnectionInterface {
    @Throws(IOException::class)
    fun init(context: Context)
    fun setCallback(callback: MqttCallbackExtended?)

    public fun connect()

    fun subscribeToTopic()

    fun isConnected(): Boolean

    public fun publishToTopic(payload:String)
}