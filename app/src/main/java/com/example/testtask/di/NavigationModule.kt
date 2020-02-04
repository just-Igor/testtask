package com.example.testtask.di

import com.example.testtask.ui.navigation.MoviesNavigationViewModel
import com.example.testtask.ui.root.RootViewModel
import com.example.testtask.usecase.navigation.INavigationUseCase
import com.example.testtask.usecase.navigation.NavigationUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router

val navigationModule = module {

    single<Cicerone<Router>> { Cicerone.create() }
    single<Router> { get<Cicerone<Router>>().router }
    single<NavigatorHolder> { get<Cicerone<Router>>().navigatorHolder }

    single<INavigationUseCase> { NavigationUseCase(get()) }
    viewModel { RootViewModel(get()) }

    viewModel { MoviesNavigationViewModel(get()) }
}
