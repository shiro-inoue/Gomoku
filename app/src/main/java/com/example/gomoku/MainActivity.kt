package com.example.gomoku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startButton.setOnClickListener {
//            startButton.text = "押されました"
//            setContentView(R.layout.activity_game)
            val dataState  = Stone(13, true, false)
            Log.d( "sendObject::", dataState .toString() )

            val intent = Intent(applicationContext, GameActivity::class.java)
//            val classData = "設定データ"
            intent.putExtra("CLASSDATA", dataState )
            startActivity(intent)
        }
    }
}