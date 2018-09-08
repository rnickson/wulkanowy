package io.github.wulkanowy.ui.main.grade.summary

import io.github.wulkanowy.ui.base.BaseView

interface GradeSummaryView : BaseView {

    fun initView()

    fun updateDataSet(data: List<GradeSummaryItem>)

    fun showProgress(show: Boolean)

    fun showRefresh(show: Boolean)

    fun predictedString(): String

    fun finalString(): String
}
