package io.github.wulkanowy.ui.splash

import io.github.wulkanowy.data.ErrorHandler
import io.github.wulkanowy.data.repositories.SessionRepository
import io.github.wulkanowy.ui.base.BasePresenter
import io.github.wulkanowy.utils.schedulers.SchedulersManager
import javax.inject.Inject

class SplashPresenter @Inject constructor(private val sessionRepository: SessionRepository,
                                          private val errorHandler: ErrorHandler,
                                          private val schedulers: SchedulersManager)
    : BasePresenter<SplashView>(errorHandler) {

    override fun attachView(view: SplashView) {
        super.attachView(view)
        disposable.add(sessionRepository.initLastSession()
                .subscribeOn(schedulers.backgroundThread())
                .observeOn(schedulers.mainThread())
                .subscribe({ view.run { if (it) openMainActivity() else openLoginActivity() } },
                        { errorHandler.proceed(it) }))
    }
}
