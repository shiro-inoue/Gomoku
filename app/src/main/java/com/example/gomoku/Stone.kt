package com.example.gomoku

import android.widget.Button

/**
 * 石のステータス
 */
enum class StoneState {
    /** 空 */
    EMPTY,
    /** 黒 */
    BLACK,
    /** 白 */
    WHITE
}

/**
 * 石情報クラス
 *
 * @constructor
 *
 * 石の状態と石にリンクしているボタンを設定
 *
 * @property state 石の状態
 * @property btn 石にリンクしているボタンのオブジェクト
 *
 */
class Stone (var state: StoneState, var btn: Button)
