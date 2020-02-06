package com.example.testtask.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testtask.domain.Error
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

    private val _loadingProgress: MutableLiveData<Boolean> = MutableLiveData()
    val loadingProgress: LiveData<Boolean> get() = _loadingProgress

    private val _error: MutableLiveData<Error> = MutableLiveData()
    val error: LiveData<Error> get() = _error

    private val disposables = CompositeDisposable()

    protected fun Disposable.addToDisposables() {
        disposables.add(this)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    private fun showError(errorMessage: String) {
        _error.postValue(Error.ErrorMessage(errorMessage))
    }

    fun closeError() {
        _error.postValue(Error.ClosedError)
    }

    protected fun startLoading(executionBlock: () -> Unit) {
        _loadingProgress.postValue(true)
        executionBlock()
    }

    protected fun finishLoading() {
        _loadingProgress.postValue(false)
    }

    protected fun processError(throwable: Throwable) {
        throwable.printStackTrace()
        throwable.localizedMessage?.let { message ->
            showError(message)
        }
    }
}
