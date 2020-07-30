package com.example.gomoku

import java.io.Serializable

/**
 * 設定データクラス
 */
class Setting (
    /**　路盤　*/
    var roadbed: Int,
    /**　三三禁　*/
    var forbiddenHand33: Boolean,
    /**　四四禁　*/
    var forbiddenHand44: Boolean,
    /**　長連　*/
    var forbiddenHandChohren: Boolean
): Serializable {

    companion object {
        // Settingクラス取得用キー
        const val KEYSETTING = "SETTING"
    }
}
