package com.mazzouzi.memoryleak.ui.singleton.solution

import android.app.Activity
import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object DialogUtilSolution {

    fun showError(context: Context?, title: String, message: String) {
        if (context == null || (context as Activity).isFinishing) {
            return
        }

        MaterialAlertDialogBuilder(context)
            .setCancelable(true)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK", null)
            .create()
            .show()
    }

    // fun showSuccess(context, )...

    // fun showLoader(context, )...
}