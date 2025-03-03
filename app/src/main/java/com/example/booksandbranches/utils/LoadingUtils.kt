package com.example.booksandbranches.utils

import android.app.ProgressDialog
import android.content.Context

class LoadingUtils(private val context: Context) {

    private var progressDialog: ProgressDialog? = null

    /**
     * Shows a loading dialog with a custom message.
     *
     * @param message The message to display in the loading dialog.
     */
    fun showLoading(message: String = "Loading...") {
        if (progressDialog == null) {
            progressDialog = ProgressDialog(context).apply {
                setMessage(message)
                setCancelable(false) // Prevent user from dismissing the dialog by tapping outside
                setProgressStyle(ProgressDialog.STYLE_SPINNER)
            }
        }
        progressDialog?.show()
    }

    /**
     * Hides the loading dialog if it is currently showing.
     */
    fun hideLoading() {
        progressDialog?.dismiss()
        progressDialog = null
    }
}