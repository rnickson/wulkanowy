package io.github.wulkanowy.ui.main.exam

import io.github.wulkanowy.data.ErrorHandler
import io.github.wulkanowy.data.db.entities.Exam
import io.github.wulkanowy.data.repositories.ExamRepository
import io.github.wulkanowy.ui.base.BasePresenter
import io.github.wulkanowy.utils.schedulers.SchedulersManager
import javax.inject.Inject

class ExamPresenter @Inject constructor(
        errorHandler: ErrorHandler,
        private val schedulers: SchedulersManager,
        private val examRepository: ExamRepository
) : BasePresenter<ExamView>(errorHandler) {

    override fun attachView(view: ExamView) {
        super.attachView(view)
        view.initView()
    }

    fun loadData(forceRefresh: Boolean = false) {

    }

    private fun createExamItems(items: Map<String, List<Exam>>) {

    }

    fun onUpdateDataList(size: Int) {

    }
}
