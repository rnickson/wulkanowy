package io.github.wulkanowy.ui.main.grade

import io.github.wulkanowy.ui.base.BaseView

interface GradeView : BaseView {

    fun initView()

    fun showContent(show: Boolean)

    fun showProgress(show: Boolean)
}

