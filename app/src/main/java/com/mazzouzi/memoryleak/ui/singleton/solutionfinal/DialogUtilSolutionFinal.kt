package com.mazzouzi.memoryleak.ui.singleton.solutionfinal

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object DialogUtilSolutionFinal {

    private var dialog: AlertDialog? = null

    fun showError(context: Context?, title: String, message: String) {
        if (context == null || (context as Activity).isFinishing) {
            return
        }

        dialog = MaterialAlertDialogBuilder(context)
            .setCancelable(true)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK", null)
            .create()

        dialog?.show()
    }

    fun clearDialog() {
        dialog?.dismiss()
        dialog = null
    }

    // fun showSuccess(context, )...

    // fun showLoader(context, )...
}




