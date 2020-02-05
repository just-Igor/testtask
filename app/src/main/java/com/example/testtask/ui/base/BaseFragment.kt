package com.example.testtask.ui.base

import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.testtask.R
import com.example.testtask.domain.Error

abstract class BaseFragment : Fragment() {

    protected abstract fun hideError()

    protected fun showError(error: Error) {
        when (error) {
            is Error.ErrorMessage -> showErrorMessage(error.message)
        }
    }

    private fun showErrorMessage(errorMessage: String) {
        context?.let {
            AlertDialog.Builder(it)
                .setTitle(R.string.warning)
                .setMessage(errorMessage)
                .setCancelable(false)
                .setPositiveButton(R.string.ok) { _, _ -> hideError() }
                .create()
                .show()
        }
    }

    protected fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
