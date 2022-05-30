package com.example.testtask.presentation.app

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.testtask.R
import com.example.testtask.core.base.BaseActivity
import com.example.testtask.core.utils.IBackButtonListener
import com.example.testtask.core.utils.Screens
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator

class MainActivity : BaseActivity() {
    override val layoutResId: Int
        get() = R.layout.activity_main

    private val router: Router by inject()

    private val navigatorHolder: NavigatorHolder by inject()

    private val viewModel: AppViewModel by viewModel()

    private val navigator = SupportAppNavigator(this, supportFragmentManager, R.id.flContainer)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.openRootScreen.observe(this) { router.newRootScreen(Screens.GifsScreen) }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        val fragment: Fragment? = supportFragmentManager.findFragmentById(R.id.flContainer)
        if (fragment != null && fragment is IBackButtonListener && (fragment as IBackButtonListener).onBackPressed()) {
            return
        } else {
            super.onBackPressed()
        }
    }
}
