package io.github.wulkanowy.ui.main.grade

import io.github.wulkanowy.data.db.entities.Grade
import io.github.wulkanowy.ui.base.BaseView

interface GradeView : BaseView {

    fun initView()

    fun openSummaryView()

    fun updateData(data: List<GradeHeader>)

    fun showGradeDialog(grade: Grade)

    fun showSemesterDialog(selectedIndex: Int)

    fun showEmptyView(show: Boolean)

    fun showContentView(show: Boolean)

    fun showProgress(show: Boolean)

    fun isViewEmpty(): Boolean

    fun showRefresh(show: Boolean)

    fun emptyAverageString(): String

    fun averageString(): String

    fun gradeNumberString(number: Int): String

    fun weightString(): String
}
