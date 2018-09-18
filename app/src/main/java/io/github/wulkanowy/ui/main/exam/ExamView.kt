package io.github.wulkanowy.ui.main.exam

import io.github.wulkanowy.data.db.entities.Exam
import io.github.wulkanowy.ui.base.BaseView

interface ExamView : BaseView {

    fun initView()

    fun updateData(data: List<ExamHeader>)

    fun showEmpty(show: Boolean)

    fun showProgress(show: Boolean)

    fun showContent(show: Boolean)

    fun showRefresh(show: Boolean)

    fun showExamDialog(exam: Exam)
}
