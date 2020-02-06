package com.example.testtask.ui.base

import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.testtask.R
import com.example.testtask.domain.Error

abstract class BaseFragment : Fragment() {

    private lateinit var errorDialog: AlertDialog

    protected abstract fun onErrorMessageButtonClick()

    protected fun handleError(error: Error) {
        when (error) {
            is Error.ErrorMessage -> showErrorMessage(error.message)
            is Error.ClosedError -> closeError()
        }
    }

    private fun showErrorMessage(errorMessage: String) {
        context?.let {
            errorDialog = AlertDialog.Builder(it)
                .setTitle(R.string.warning)
                .setMessage(errorMessage)
                .setCancelable(false)
                .setPositiveButton(R.string.ok, null)
                .create().apply {
                    setOnShowListener {
                        errorDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                            onErrorMessageButtonClick()
                        }
                    }
                    show()
                }
        }
    }

    private fun closeError() {
        if (::errorDialog.isInitialized && errorDialog.isShowing) {
            errorDialog.dismiss()
        }
    }

    protected fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
