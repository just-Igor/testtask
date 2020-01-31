package com.example.testtask.ui.root

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.testtask.R
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command

class RootActivity : AppCompatActivity() {

    private val navigatorHolder: NavigatorHolder by inject()

    private val navigator = object: SupportAppNavigator(this, R.id.fl_container) {
        override fun setupFragmentTransaction(command: Command?, currentFragment: Fragment?,
                                              nextFragment: Fragment?, fragmentTransaction: FragmentTransaction?) {
            fragmentTransaction?.setCustomAnimations(R.anim.enter_animation, R.anim.exit_animation)
        }
    }

    private val viewModel: RootViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigatorHolder.setNavigator(navigator)
        if (savedInstanceState == null) {
            viewModel.openRootScreen()
        }
    }
}
