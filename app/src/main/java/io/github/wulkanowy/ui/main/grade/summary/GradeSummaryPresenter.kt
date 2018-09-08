package io.github.wulkanowy.ui.main.grade.summary

import io.github.wulkanowy.data.ErrorHandler
import io.github.wulkanowy.data.db.entities.GradeSummary
import io.github.wulkanowy.data.repositories.GradeSummaryRepository
import io.github.wulkanowy.data.repositories.SessionRepository
import io.github.wulkanowy.ui.base.BasePresenter
import io.github.wulkanowy.utils.schedulers.SchedulersManager
import javax.inject.Inject

class GradeSummaryPresenter @Inject constructor(
        private val errorHandler: ErrorHandler,
        private val gradeSummaryRepository: GradeSummaryRepository,
        private val sessionRepository: SessionRepository,
        private val schedulers: SchedulersManager)
    : BasePresenter<GradeSummaryView>(errorHandler) {

    override fun attachView(view: GradeSummaryView) {
        super.attachView(view)
        view.initView()
    }

    fun loadData(forceRefresh: Boolean = false) {
        disposable.add(sessionRepository.getSemesters().map { semester -> semester.single { it.current } }
                .flatMap { gradeSummaryRepository.getGradesSummary(it, forceRefresh) }
                .map { createGradeSummaryItems(it) }
                .subscribeOn(schedulers.backgroundThread())
                .observeOn(schedulers.mainThread())
                .doFinally {
                    view?.run {
                        showProgress(false)
                        showRefresh(false)
                    }
                }
                .subscribe({ view?.updateDataSet(it) }) { errorHandler.proceed(it) })
    }

    private fun createGradeSummaryItems(gradesSummary: List<GradeSummary>): List<GradeSummaryItem> {
        return gradesSummary.flatMap {
            GradeSummaryHeader().apply {
                average = ""
                name = it.subject
            }.let { header ->
                listOf(GradeSummaryItem(header).apply {
                    grade = it.predictedGrade
                    title = view?.predictedString().orEmpty()
                }, GradeSummaryItem(header).apply {
                    grade = it.finalGrade
                    title = view?.finalString().orEmpty()
                }
                )
            }
        }
    }
}
