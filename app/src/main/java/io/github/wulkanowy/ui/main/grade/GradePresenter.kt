package io.github.wulkanowy.ui.main.grade

import io.github.wulkanowy.data.ErrorHandler
import io.github.wulkanowy.ui.base.BasePresenter
import io.github.wulkanowy.utils.schedulers.SchedulersManager
import io.reactivex.Completable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class GradePresenter @Inject constructor(
        errorHandler: ErrorHandler,
        private val schedulers: SchedulersManager) : BasePresenter<GradeView>(errorHandler) {

    override fun attachView(view: GradeView) {
        super.attachView(view)
        disposable.add(Completable.timer(150, TimeUnit.MILLISECONDS, schedulers.mainThread())
                .subscribe {
                    view.run {
                        initView()
                        showContent(true)
                        showProgress(false)
                    }
                })
    }
}

