package com.example.gomoku

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class SettingsDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context!!)
        val inflater = activity!!.layoutInflater
        val signinView = inflater.inflate(R.layout.dialog_settings, null)
        var selectRoadbed: Int? = 0

        val roadbed = arguments?.getInt("roadbed")
        val roadbedMap: Map<Int, Int> = mapOf(19 to 0, 15 to 1, 13 to 2, 9 to 3)
        val index = roadbedMap[roadbed]

        val roadbedList = arrayOf("19路盤", "15路盤", "13路盤", "9路盤")

        if (index != null) {

            builder.setView(signinView)
                .setTitle(R.string.app_settingsDialog)
                .setSingleChoiceItems(roadbedList, index) { dialog, which ->
                    selectRoadbed = which
                }
                .setPositiveButton(R.string.app_settingsButton) { dialog, id ->
                    mLister?.onDialogPositiveClick(selectRoadbed)
                }
                .setNegativeButton(R.string.app_cancelButton) { dialog, id ->
                }
        }

        return builder.create()
    }

    companion object {
        fun newInstance(setting: Setting): SettingsDialog {
            val fragment = SettingsDialog()
            val args = Bundle()
            args.putInt("roadbed", setting.roadbed)
            fragment.arguments = args
            return fragment
        }
    }

    public interface NoticeDialogLister {
        public fun onDialogPositiveClick(index: Int?)
    }

    var mLister: NoticeDialogLister? = null

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        try {
            mLister = context as NoticeDialogLister
        } catch (e: ClassCastException) {
            throw ClassCastException("${context.toString()} must implement NoticeDialogListener")
        }
    }
}
