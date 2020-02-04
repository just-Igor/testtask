package com.example.testtask.ui.root

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.testtask.R
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder

class RootActivity : AppCompatActivity() {

    private val navigatorHolder: NavigatorHolder by inject()
    private val navigator: Navigator by inject { parametersOf(this) }

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
