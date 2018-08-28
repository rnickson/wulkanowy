package io.github.wulkanowy.ui.main.grade

import io.github.wulkanowy.data.ErrorHandler
import io.github.wulkanowy.data.repositories.GradeRepository
import io.github.wulkanowy.data.repositories.SessionRepository
import io.github.wulkanowy.ui.base.BasePresenter
import io.github.wulkanowy.utils.calcAverage
import io.github.wulkanowy.utils.schedulers.SchedulersManager
import javax.inject.Inject

class GradePresenter @Inject constructor(
        private val errorHandler: ErrorHandler,
        private val schedulers: SchedulersManager,
        private val gradeRepository: GradeRepository,
        private val sessionRepository: SessionRepository) : BasePresenter<GradeView>(errorHandler) {

    override fun attachView(view: GradeView) {
        super.attachView(view)
        view.initView()
    }

    fun loadData() {
        disposable.add(sessionRepository.getSemesters()
                .map { it.single { semester -> semester.current } }
                .flatMap { gradeRepository.getGrades(it) }
                .map { it.groupBy { grade -> grade.subject } }
                .map { items ->
                    items.map {
                        GradeHeader().apply {
                            subject = it.key
                            average = calcAverage(it.value)
                            number = it.value.size
                            subItems = (it.value.map { item ->
                                GradeItem().apply { grade = item }
                            })
                        }
                    }
                }
                .subscribeOn(schedulers.backgroundThread())
                .observeOn(schedulers.mainThread())
                .doOnSuccess { if (it.isEmpty()) view?.showEmptyView(true) }
                .doOnError { view?.run { if (isViewEmpty()) showEmptyView(true) } }
                .subscribe({ view?.updateData(it) }) { errorHandler.proceed(it) })
    }

    fun onUpdateDataList(size: Int) {
        if (size != 0) view?.showProgress(false)
    }
}
