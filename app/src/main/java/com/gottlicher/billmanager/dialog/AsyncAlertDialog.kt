package com.gottlicher.billmanager.dialog

import android.app.AlertDialog
import android.content.Context
import kotlinx.coroutines.experimental.CompletableDeferred

enum class DialogResult {
    NONE, POSITIVE, NEGATIVE, NEUTRAL, DISMISSED, ERROR
}

class AsyncAlertDialog (
        val title: Int? = null,
        val message: Int? = null,
        val positiveButtonText: Int? = null,
        val negativeButtonText: Int? = null,
        val neutralButtonText: Int? = null) {

    suspend fun show (context: Context): DialogResult {

        val completion = CompletableDeferred<DialogResult> ()

        val builder = AlertDialog.Builder (context)
        if (title != null) { builder.setTitle(title) }
        if (message != null) { builder.setMessage(message) }
        if (positiveButtonText != null) { builder.setPositiveButton(positiveButtonText) { _, _ -> completion.complete(DialogResult.POSITIVE)} }
        if (negativeButtonText != null) { builder.setNegativeButton(negativeButtonText) { _, _ -> completion.complete(DialogResult.NEGATIVE)} }
        if (neutralButtonText != null) { builder.setNeutralButton(neutralButtonText) { _, _ -> completion.complete(DialogResult.NEUTRAL)} }

        builder.create().show()
        return completion.await()
    }
}