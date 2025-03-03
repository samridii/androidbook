package com.example.booksandbranches.utils

import android.app.ProgressDialog
import android.content.Context

class LoadingUtils(private val context: Context) {
    private var progressDialog: ProgressDialog? = null

    fun showLoading(message: String = "Loading...") {
        // Create and show the ProgressDialog
        progressDialog = ProgressDialog(context).apply {
            setMessage(message)
            setCancelable(false)
            show()
        }
    }

    fun dismiss() {
        // Dismiss the ProgressDialog if it's showing
        progressDialog?.dismiss()
        progressDialog = null
    }
}
