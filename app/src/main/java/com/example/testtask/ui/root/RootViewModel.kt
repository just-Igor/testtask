package com.example.testtask.ui.root

import com.example.testtask.ui.base.BaseViewModel
import com.example.testtask.usecase.navigation.INavigationUseCase

class RootViewModel(private val navigationUseCase: INavigationUseCase) : BaseViewModel() {

    fun openRootScreen() {
        navigationUseCase.openRootScreen()
    }
}
