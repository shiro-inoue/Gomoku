package com.example.gomoku

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

/**
 * 設定ダイアログクラス
 */
class SettingsDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //参考サイト
        //http://www.366service.com/jp/qa/39fcb9b752f2a84833331fdc69a31381　　　ポップアップdialogFragmentにデータを渡したい
        //https://101010.fun/posts/android-try-dialog.html#header-7   ダイアログからアクティビティへコールバックする

        val builder = AlertDialog.Builder(context!!)
        val inflater = activity!!.layoutInflater
        val signinView = inflater.inflate(R.layout.dialog_settings, null)
        var selectRoadbed: Int? = 0

        val roadbed = arguments?.getInt(KEYROADBED)
        // 路盤選択RadioButtonがXMLに実装できるまでの仮実装
        val roadbedMap: Map<Int, Int> = mapOf(19 to 0, 15 to 1, 13 to 2, 9 to 3)
        val index = roadbedMap[roadbed]

        // 本当はXMLで表示したいが、RadioButton取得・設定ができなかったため、ここで定義
        val roadbedList = arrayOf("19路盤", "15路盤", "13路盤", "9路盤")

        if (index != null) {

            builder.setView(signinView)
                .setTitle(R.string.app_settingsDialog)
                .setSingleChoiceItems(roadbedList, index) { dialog, which ->
                    selectRoadbed = which
                }
                .setPositiveButton(R.string.app_settingsButton) { dialog, id ->
                    // コールバック関数
                    mLister?.onDialogPositiveClick(selectRoadbed)
                }
                .setNegativeButton(R.string.app_cancelButton) { dialog, id ->
                }
        }

        return builder.create()
    }

    companion object {
        // 路盤取得用キー
        const val KEYROADBED = "ROADBED"

        /**
         * パラメータ引き渡し用関数
         */
        fun newInstance(setting: Setting): SettingsDialog {
            val fragment = SettingsDialog()
            val args = Bundle()
            args.putInt(KEYROADBED, setting.roadbed)
            fragment.arguments = args
            return fragment
        }
    }

    /**
     * コールバック用インターフェース
     */
    public interface NoticeDialogLister {
        public fun onDialogPositiveClick(index: Int?)
    }

    var mLister: NoticeDialogLister? = null

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        try {
            // MainActivityをNoticeDialogListerへキャスト
            mLister = context as NoticeDialogLister
        } catch (e: ClassCastException) {
            throw ClassCastException("${context.toString()} must implement NoticeDialogListener")
        }
    }
}
