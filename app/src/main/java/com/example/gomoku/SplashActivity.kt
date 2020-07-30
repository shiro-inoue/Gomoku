package com.example.gomoku

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.*

/**
 * スプラッシュ画面用 activity
 */
class SplashActivity : AppCompatActivity() {
    /** スレッドを管理するための Android システムのフレームワーク */
    private val mHandler = Handler()

    /** 別のスレッドで実行する */
    private val mRunnable = Runnable {

        // MainActivityへ遷移させる
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

        // ここでfinish()を呼ばないとMainActivityでAndroidの戻るボタンを押すとスプラッシュ画面に戻ってしまう
        finish()
    }

    /**
     * Activityが生成された最初の１回だけ呼び出される
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // スプラッシュ画面の
        val list = listOf(  R.drawable.splash01,
                            R.drawable.splash02,
                            R.drawable.splash03)

        // 乱数生成
        val index = Random().nextInt(list.size)
        // スプラッシュ画面を設定
        splashImage.background = getDrawable(list[index])

        // スプラッシュ表示1000ms(1秒)後にrunnableを呼んでMeinActivityへ遷移させる
        mHandler.postDelayed(mRunnable, 5000)
    }

    /**
     * Activityが画面から見えなくなる時に呼び出される。
     */
    override fun onStop() {
        super.onStop()

        // スプラッシュ画面中にアプリを落とした時にはrunnableが呼ばれないようにする
        // これがないとアプリを消した後にまた表示される
        mHandler.removeCallbacks(mRunnable)
    }
}