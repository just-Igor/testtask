package com.example.testtask.di

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.example.testtask.R
import com.example.testtask.ui.navigation.MoviesNavigationViewModel
import com.example.testtask.ui.root.RootViewModel
import com.example.testtask.usecase.navigation.INavigationUseCase
import com.example.testtask.usecase.navigation.NavigationUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command

val navigationModule = module {

    single<Cicerone<Router>> { Cicerone.create() }
    single<Router> { get<Cicerone<Router>>().router }
    single<NavigatorHolder> { get<Cicerone<Router>>().navigatorHolder }
    factory<Navigator> { (activity: FragmentActivity) ->
        object : SupportAppNavigator(activity, R.id.fcvContainer) {
            override fun setupFragmentTransaction(
                command: Command?,
                currentFragment: Fragment?,
                nextFragment: Fragment?,
                fragmentTransaction: FragmentTransaction?
            ) {
                fragmentTransaction?.setCustomAnimations(R.anim.enter_animation, R.anim.exit_animation)
            }
        }
    }

    single<INavigationUseCase> { NavigationUseCase(get()) }
    viewModel { RootViewModel(get()) }

    viewModel { MoviesNavigationViewModel(get()) }
}
