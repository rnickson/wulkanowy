package io.github.wulkanowy.ui.main.grade.summary

import io.github.wulkanowy.ui.base.BaseView

interface GradeSummaryView : BaseView {

    fun initView()

    fun popView()

    fun updateDataSet(data: List<GradeSummaryItem>, finalAvg: String, calculatedAvg: String)

    fun showProgress(show: Boolean)

    fun showRefresh(show: Boolean)

    fun showContent(show: Boolean)

    fun showEmpty(show: Boolean)

    fun predictedString(): String

    fun finalString(): String
}
