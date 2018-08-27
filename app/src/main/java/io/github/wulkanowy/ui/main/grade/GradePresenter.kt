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
                .subscribeOn(schedulers.backgroundThread())
                .observeOn(schedulers.mainThread())
                .subscribe({ list ->
                    view?.updateData(list.map { map ->
                        GradeHeader(
                                subject = map.key,
                                average = calcAverage(map.value).toString(),
                                number = map.value.size.toString()
                        ).apply {
                            subItems = (map.value.map {
                                GradeItem(it, view?.weightString().orEmpty())
                            })
                        }
                    })
                }) { errorHandler.proceed(it) })
    }
}

