package com.example.testtask.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

    private val _loadingProgress: MutableLiveData<Boolean> = MutableLiveData()
    val loadingProgress: LiveData<Boolean> get() = _loadingProgress

    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val disposables = CompositeDisposable()

    fun Disposable.addToDisposables() {
        disposables.add(this)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    private fun showErrorMessage(errorMessage: String) {
        _errorMessage.postValue(errorMessage)
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
            showErrorMessage(message)
        }
    }
}
