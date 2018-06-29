package com.example.Jorge.projetosd

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    lateinit var receiver: CarregadorReceiverInterno
    lateinit var filter: IntentFilter
    lateinit var texto: TextView
    var connected: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.texto = findViewById(R.id.tvMainTexto)

        this.receiver = CarregadorReceiverInterno()

        this.filter = IntentFilter()
        this.filter.addAction(Intent.ACTION_POWER_CONNECTED)
        this.filter.addAction(Intent.ACTION_POWER_DISCONNECTED)

        this.filter.addAction(Intent.ACTION_BATTERY_CHANGED)
    }

    override fun onResume() {
        super.onResume()

        registerReceiver(this.receiver, this.filter)
    }

    override fun onStop() {
        super.onStop()

        unregisterReceiver(this.receiver)
    }

    inner class CarregadorReceiverInterno: BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {

            if(intent?.action == Intent.ACTION_POWER_CONNECTED ){
                connected = true
                this@MainActivity.texto.text = "Conectou"
            }
            else if(intent?.action == Intent.ACTION_POWER_DISCONNECTED){
                connected = false
                this@MainActivity.texto.text = "Desconectou"
            }
            if(intent?.action == Intent.ACTION_BATTERY_CHANGED && connected){
                val battery = intent.getIntExtra("level",-1)
                Toast.makeText(context, "${battery}%", Toast.LENGTH_SHORT).show()
                connected = false

            }
        }
    }
}
