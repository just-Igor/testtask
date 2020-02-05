package com.example.testtask.domain

sealed class Error {
    data class ErrorMessage(val message: String) : Error()
    object ClosedError : Error()
}
