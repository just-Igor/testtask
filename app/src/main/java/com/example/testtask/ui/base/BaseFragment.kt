package com.example.testtask.ui.base

import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.testtask.R
import com.example.testtask.domain.Error

abstract class BaseFragment : Fragment() {

    private val errorDialog: AlertDialog by lazy {
        AlertDialog.Builder(context!!)
            .setTitle(R.string.warning)
            .setCancelable(false)
            .setPositiveButton(R.string.ok, null)
            .create()
            .apply {
                setOnShowListener {
                    errorDialog.getButton(AlertDialog.BUTTON_POSITIVE)?.setOnClickListener {
                        onErrorMessageButtonClick()
                    }
                }
            }
    }

    protected abstract fun onErrorMessageButtonClick()

    protected fun handleError(error: Error) {
        when (error) {
            is Error.ErrorMessage -> showErrorMessage(error.message)
            is Error.ClosedError -> closeError()
        }
    }

    private fun showErrorMessage(errorMessage: String) {
        errorDialog.setMessage(errorMessage)
        errorDialog.show()
    }

    private fun closeError() {
        if (errorDialog.isShowing == true) {
            errorDialog.dismiss()
        }
    }

    protected fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
