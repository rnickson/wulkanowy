package io.github.wulkanowy.ui.splash

import io.github.wulkanowy.ui.base.BaseView

interface SplashView : BaseView {

    fun openLoginActivity()

    fun openMainActivity()

    fun finishApp()
}
