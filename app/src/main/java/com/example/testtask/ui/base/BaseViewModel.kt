package com.example.testtask.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.testtask.utils.liveData.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel: ViewModel() {

    private val _loadingProgress: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val loadingProgress: LiveData<Boolean> get() = _loadingProgress

    private val _errorMessage: SingleLiveEvent<String> = SingleLiveEvent()
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