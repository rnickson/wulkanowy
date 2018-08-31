package io.github.wulkanowy.ui.main

import io.github.wulkanowy.ui.base.BaseView

interface MainView : BaseView {

    fun initView()

    fun switchMenuFragment(position: Int)

    fun setViewTitle(title: String)

    fun defaultTitle(): String

    fun mapOfTitles(): Map<Int, String>

    fun showActionBar(show: Boolean)
}
