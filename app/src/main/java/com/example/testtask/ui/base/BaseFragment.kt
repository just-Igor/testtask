package com.example.testtask.ui.base

import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.testtask.R

abstract class BaseFragment: Fragment() {

    protected fun showError(errorMessage: String) {
        context?.let {
            AlertDialog.Builder(it)
                .setTitle(R.string.warning)
                .setMessage(errorMessage)
                .setCancelable(false)
                .setPositiveButton(R.string.ok) { dialog, which -> dialog?.dismiss() }
                .create()
                .show()
        }
    }

    protected fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}