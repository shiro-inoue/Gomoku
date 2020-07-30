package com.example.gomoku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SettingsDialog.NoticeDialogLister {
    private var setting = Setting(13, false, false, true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startButton.setOnClickListener {
            val intent = Intent(applicationContext, GameActivity::class.java)
            intent.putExtra("SETTING", setting)
            startActivity(intent)
        }

        settingsButton.setOnClickListener {
            val dialog = SettingsDialog.newInstance(setting)
            dialog.show(supportFragmentManager, "simple")
        }
    }

    private fun convertRoadbed(index: Int?) {
        val roadbedMap: Map<Int, Int> = mapOf(0 to 19, 1 to 15, 2 to 13, 3 to 9)
        if (index != null) {
            setting.roadbed = roadbedMap[index]!!
        }
    }

    override fun onDialogPositiveClick(index: Int?) {
        convertRoadbed(index)
    }
}