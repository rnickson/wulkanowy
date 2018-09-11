package io.github.wulkanowy.ui.main.grade

import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import io.github.wulkanowy.data.ErrorHandler
import io.github.wulkanowy.data.db.entities.Grade
import io.github.wulkanowy.data.db.entities.Semester
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

    private var selectedSemester = -1

    override fun attachView(view: GradeView) {
        super.attachView(view)
        view.initView()
    }

    fun loadData(forceRefresh: Boolean = false, semesterIndex: Int = -1) {
        disposable.add(sessionRepository.getSemesters()
                .map { selectSemester(it, if (semesterIndex != -1) semesterIndex else selectedSemester) }
                .flatMap {
                    selectedSemester = it.semesterName - 1
                    gradeRepository.getGrades(it, forceRefresh)
                }
                .map { it.groupBy { grade -> grade.subject }.toSortedMap() }
                .map { createGradeItems(it) }
                .subscribeOn(schedulers.backgroundThread())
                .observeOn(schedulers.mainThread())
                .doFinally {
                    view?.run {
                        showRefresh(false)
                        showProgress(false)
                    }
                }
                .doAfterSuccess {
                    view?.run {
                        showEmpty(it.isEmpty())
                        showContent(it.isNotEmpty())
                    }
                }
                .subscribe({ view?.updateData(it) }) { errorHandler.proceed(it) })
    }

    fun onGradeItemSelected(item: AbstractFlexibleItem<*>?) {
        if (item is GradeItem) view?.showGradeDialog(item.grade)
    }

    fun onSemesterSelected(index: Int) {
        view?.run {
            showEmpty(false)
            showProgress(true)
            showContent(false)
        }
        loadData(semesterIndex = index)
    }

    fun onSemesterSwitchSelected(): Boolean {
        if (selectedSemester != -1) view?.showSemesterDialog(selectedSemester)
        return true
    }

    fun onSummarySwitchSelected(): Boolean {
        view?.openSummaryView()
        return true
    }

    fun onUpdateDataList(size: Int) {
        if (size != 0) view?.showProgress(false)
    }

    private fun selectSemester(semesters: List<Semester>, index: Int): Semester {
        return semesters.single { it.current }.let { currentSemester ->
            if (index == -1) currentSemester
            else semesters.single { semester ->
                semester.run {
                    semesterName - 1 == index && diaryId == currentSemester.diaryId
                }
            }
        }
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
                subItems = (it.value.reversed().map { item ->
                    GradeItem().apply {
                        grade = item
                        weightString = view?.weightString().orEmpty()
                        valueColor = getValueColor(item.value)
                    }
                })
            }
        }
    }
}
