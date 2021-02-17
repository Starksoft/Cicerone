package com.github.terrakok.cicerone.sample.mvp.start

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.sample.Screens
import com.github.terrakok.cicerone.sample.Screens.BottomNavigation
import com.github.terrakok.cicerone.sample.Screens.Profile
import moxy.MvpPresenter

/**
 * Created by terrakok 21.11.16
 */
class StartActivityPresenter(private val router: Router) : MvpPresenter<StartActivityView>() {

    fun onOrdinaryPressed() {
        router.navigateTo(Screens.MAIN)
    }

    fun onMultiPressed() {
        router.navigateTo(BottomNavigation())
    }

    fun onResultWithAnimationPressed() {
        router.navigateTo(Profile())
    }

    fun onBackPressed() {
        router.exit()
    }

}
