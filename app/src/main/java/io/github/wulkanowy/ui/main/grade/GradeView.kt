package io.github.wulkanowy.ui.main.grade

import io.github.wulkanowy.ui.base.BaseView

interface GradeView : BaseView {

    fun initView()

    fun updateData(data: List<GradeHeader>)

    fun showEmptyView(show: Boolean)

    fun showProgress(show: Boolean)

    fun isViewEmpty(): Boolean
}
