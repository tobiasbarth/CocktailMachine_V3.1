package com.app.cocktailmachine.connections


import android.content.Context
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*

object MqttConnection: MQTTConnectionInterface {
    lateinit var mqttAndroidClient: MqttAndroidClient
    val serverUri = "tcp://192.168.178.60:1883"
    val clientId = "App"
    val publishTopic = "app_pub"
    val subscriptionTopic = "app_sub"
    val username = "user"
    val password = "pwd"
    var is_connected = false


    override fun setCallback(callback: MqttCallbackExtended?) {
        mqttAndroidClient.setCallback(callback)
    }

    override public fun connect() {
        val mqttConnectOptions = MqttConnectOptions()
        mqttConnectOptions.isAutomaticReconnect = true
        mqttConnectOptions.isCleanSession = false
        mqttConnectOptions.userName = username
        mqttConnectOptions.password = password.toCharArray()
        try {
            mqttAndroidClient.connect(mqttConnectOptions, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken) {
                    val disconnectedBufferOptions =
                        DisconnectedBufferOptions()

                    disconnectedBufferOptions.isBufferEnabled = true
                    disconnectedBufferOptions.bufferSize = 100
                    disconnectedBufferOptions.isPersistBuffer = false
                    disconnectedBufferOptions.isDeleteOldestMessages = false
                    mqttAndroidClient.setBufferOpts(disconnectedBufferOptions)

                    subscribeToTopic()


                }

                override fun onFailure(
                    asyncActionToken: IMqttToken,
                    exception: Throwable
                ) {
                    Log.w("Mqtt", "Failed to connect to: $serverUri$exception")
                }
            })
            this.is_connected = true
        } catch (ex: MqttException) {
            ex.printStackTrace()
        }
    }

    override fun subscribeToTopic() {
        try {
            mqttAndroidClient.subscribe(subscriptionTopic, 0, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken) {
                    Log.w("Mqtt", "Subscribed!")
                }

                override fun onFailure(
                    asyncActionToken: IMqttToken,
                    exception: Throwable
                ) {
                    Log.w("Mqtt", "Subscribed fail!")
                }
            })
        } catch (ex: MqttException) {
            System.err.println("Exception whilst subscribing")
            ex.printStackTrace()
        }
    }

    override public fun publishToTopic(payload:String){
            try {
                mqttAndroidClient.publish(
                    publishTopic,
                    payload.toByteArray(),
                    0,
                    false,
                    null,
                    object : IMqttActionListener {
                        override fun onSuccess(asyncActionToken: IMqttToken) {
                            Log.w("Mqtt", "Published!")
                        }

                        override fun onFailure(
                            asyncActionToken: IMqttToken,
                            exception: Throwable
                        ) {
                            Log.w("Mqtt", "Published fail!")
                        }
                    })

            } catch (ex: MqttException) {
                System.err.println("Exception whilst publishing")
                ex.printStackTrace()
            }
    }

    override fun init(context: Context){
        mqttAndroidClient = MqttAndroidClient(context, serverUri, clientId)
        mqttAndroidClient.setCallback(object : MqttCallbackExtended {
            override fun connectComplete(b: Boolean, s: String) {
                Log.w("mqtt", s)
            }

            override fun connectionLost(throwable: Throwable) {}

            @Throws(Exception::class)
            override fun messageArrived(
                topic: String,
                mqttMessage: MqttMessage
            ) {
                Log.w("Mqtt", mqttMessage.toString())
            }

            override fun deliveryComplete(iMqttDeliveryToken: IMqttDeliveryToken) {}
        })
        connect()
    }

    override fun isConnected(): Boolean {
        return this.is_connected
    }
}