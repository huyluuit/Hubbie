package com.example.hubbie.modules.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.hubbie.R

class ProcessDialog(private val message: String) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val v = activity?.layoutInflater?.inflate(R.layout.fragment_process_dialog, null)
        val dialog = AlertDialog.Builder(activity).setView(v).create()
        dialog.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        dialog.setCancelable(false)
        val tvMessage: TextView = v!!.findViewById(R.id.tvProcessMessage)
        tvMessage.text = message
        val btnCancel: Button = v.findViewById(R.id.btnCancel)
        btnCancel.setOnClickListener {
            dismiss()
        }
        return dialog
    }


    interface Callbacks {
        fun onCancelClicked()
    }

}