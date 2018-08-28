package io.github.wulkanowy.ui.main

import io.github.wulkanowy.data.ErrorHandler
import io.github.wulkanowy.data.repositories.SessionRepository
import io.github.wulkanowy.ui.base.BasePresenter
import io.github.wulkanowy.utils.schedulers.SchedulersManager
import javax.inject.Inject

class MainPresenter @Inject constructor(
        private val errorHandler: ErrorHandler,
        private val sessionRepository: SessionRepository,
        private val schedulers: SchedulersManager)
    : BasePresenter<MainView>(errorHandler) {

    override fun attachView(view: MainView) {
        super.attachView(view)
        view.run {
            showActionBar(false)
            initBottomNav()
            initFragmentController()
            showProgress(false)
            showActionBar(true)
        }
    }

    fun onTabSelected(position: Int): Boolean {
        view?.switchMenuFragment(position)
        return true
    }

    fun onMenuFragmentChange(position: Int) {
        view?.run {
            setViewTitle(mapOfTitles()[position] ?: defaultTitle())
        }
    }
}

