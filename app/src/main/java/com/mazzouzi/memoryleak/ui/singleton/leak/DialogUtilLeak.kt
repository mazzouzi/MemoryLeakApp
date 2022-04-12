package com.mazzouzi.memoryleak.ui.singleton.leak

import android.app.Activity
import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * A new GC root (static class) is now referencing a context : ❌
 */
class DialogUtilLeak private constructor(private val context: Context?) {

    fun showError(title: String?, message: String?) {
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

    // fun showSuccess()...

    // fun showLoader()...

    companion object {
        private var instance : DialogUtilLeak? = null // the IDE is warning you !

        fun  getInstance(context: Context): DialogUtilLeak {
            if (instance == null)  // NOT thread safe!
                instance = DialogUtilLeak(context)

            return instance!!
        }
    }
}