package io.github.wulkanowy.ui.main.grade

import io.github.wulkanowy.data.ErrorHandler
import io.github.wulkanowy.data.db.entities.Grade
import io.github.wulkanowy.data.repositories.GradeRepository
import io.github.wulkanowy.data.repositories.SessionRepository
import io.github.wulkanowy.ui.base.BasePresenter
import io.github.wulkanowy.utils.calcAverage
import io.github.wulkanowy.utils.getValueColor
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

    fun loadData(forceRefresh: Boolean = false) {
        disposable.add(sessionRepository.getSemesters()
                .map { it.single { semester -> semester.current } }
                .flatMap { gradeRepository.getGrades(it, forceRefresh) }
                .map { it.groupBy { grade -> grade.subject } }
                .map { createGradeItems(it) }
                .subscribeOn(schedulers.backgroundThread())
                .observeOn(schedulers.mainThread())
                .doFinally { view?.setRefresh(false) }
                .doOnSuccess { if (it.isEmpty()) view?.showEmptyView(true) }
                .doOnError { view?.run { if (isViewEmpty()) showEmptyView(true) } }
                .subscribe({ view?.updateData(it) }) { errorHandler.proceed(it) })
    }

    private fun createGradeItems(items: Map<String, List<Grade>>): List<GradeHeader> {
        return items.map {
            val gradesAverage = calcAverage(it.value)
            GradeHeader().apply {
                subject = it.key
                average = view?.run {
                    if (gradesAverage == 0f) emptyAverageString()
                    else averageString().format(gradesAverage)
                }.orEmpty()
                number = view?.gradeNumberString(it.value.size).orEmpty()
                subItems = (it.value.map { item ->
                    GradeItem().apply {
                        grade = item
                        weightString = view?.weightString().orEmpty()
                        valueColor = getValueColor(item.value)
                    }
                })
            }
        }
    }

    fun onUpdateDataList(size: Int) {
        if (size != 0) view?.showProgress(false)
    }
}
