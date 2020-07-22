package com.example.gomoku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startButton.setOnClickListener {
            val intent = Intent(applicationContext, GameActivity::class.java)

            val setting = Setting(13, false, true)
            intent.putExtra("SETTING", setting)

            startActivity(intent)
        }
    }
}