package io.github.wulkanowy.ui.main

import io.github.wulkanowy.data.ErrorHandler
import io.github.wulkanowy.data.repositories.StudentRepository
import io.github.wulkanowy.ui.base.BasePresenter
import io.github.wulkanowy.utils.schedulers.SchedulersManager
import javax.inject.Inject

class MainPresenter @Inject constructor(
        private val errorHandler: ErrorHandler,
        private val studentRepository: StudentRepository,
        private val schedulers: SchedulersManager)
    : BasePresenter<MainView>(errorHandler) {

    override fun attachView(view: MainView) {
        super.attachView(view)
        view.run {
            initFragmentController()
            initBottomNav()
        }
        initLastSession()
    }

    private fun initLastSession() {
        disposable.add(studentRepository.initLastStudentSession()
                .subscribeOn(schedulers.backgroundThread())
                .observeOn(schedulers.mainThread())
                .subscribe({}, { errorHandler.proceed(it) }))
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

