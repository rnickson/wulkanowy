package io.github.wulkanowy.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import com.ncapdevi.fragnav.FragNavController
import com.ncapdevi.fragnav.FragNavController.Companion.DETACH_ON_NAVIGATE_HIDE_ON_SWITCH
import io.github.wulkanowy.R
import io.github.wulkanowy.ui.base.BaseActivity
import io.github.wulkanowy.ui.main.attendance.AttendanceFragment
import io.github.wulkanowy.ui.main.exam.ExamFragment
import io.github.wulkanowy.ui.main.grade.GradeFragment
import io.github.wulkanowy.ui.main.more.MoreFragment
import io.github.wulkanowy.ui.main.timetable.TimetableFragment
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity(), MainView, FragNavController.TransactionListener {
    @Inject
    lateinit var presenter: MainPresenter

    @Inject
    lateinit var navController: FragNavController

    companion object {
        const val DEFAULT_TAB = 2

        fun getStartIntent(context: Context) = Intent(context, MainActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        messageView = mainFragmentContainer
        presenter.attachView(this)
        navController.initialize(DEFAULT_TAB, savedInstanceState)
    }

    override fun initFragmentController() {
        navController.run {
            rootFragments = listOf(
                    GradeFragment.newInstance(),
                    AttendanceFragment.newInstance(),
                    ExamFragment.newInstance(),
                    TimetableFragment.newInstance(),
                    MoreFragment.newInstance()
            )
            fragmentHideStrategy = DETACH_ON_NAVIGATE_HIDE_ON_SWITCH
            //createEager = true
            transactionListener = this@MainActivity
        }
    }

    override fun initBottomNav() {
        mainBottomNav.run {
            addItems(mutableListOf(
                    AHBottomNavigationItem(R.string.grade_title, R.drawable.ic_menu_main_grade_26dp, 0),
                    AHBottomNavigationItem(R.string.attendance_title, R.drawable.ic_menu_main_attendance_24dp, 0),
                    AHBottomNavigationItem(R.string.exam_title, R.drawable.ic_menu_main_exam_24dp, 0),
                    AHBottomNavigationItem(R.string.timetable_title, R.drawable.ic_menu_main_timetable_24dp, 0),
                    AHBottomNavigationItem(R.string.more_title, R.drawable.ic_menu_main_more_24dp, 0)
            ))
            accentColor = ContextCompat.getColor(context, R.color.colorPrimary)
            inactiveColor = ContextCompat.getColor(context, android.R.color.black)
            titleState = AHBottomNavigation.TitleState.ALWAYS_SHOW
            currentItem = DEFAULT_TAB
            isBehaviorTranslationEnabled = false
            setOnTabSelectedListener { position, _ ->
                presenter.onTabSelected(position)
            }
        }
    }

    override fun onFragmentTransaction(fragment: Fragment?, transactionType: FragNavController.TransactionType) {}

    override fun onTabTransaction(fragment: Fragment?, index: Int) {
        presenter.onMenuFragmentChange(index)
    }

    override fun switchMenuFragment(position: Int) {
        navController.switchTab(position)
    }

    override fun setViewTitle(title: String) {
        setTitle(title)
    }

    override fun defaultTitle(): String = getString(R.string.main_title)

    override fun mapOfTitles(): Map<Int, String> {
        return mapOf(0 to R.string.grade_title,
                1 to R.string.attendance_title,
                2 to R.string.exam_title,
                3 to R.string.timetable_title,
                4 to R.string.more_title
        ).mapValues { getString(it.value) }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        navController.onSaveInstanceState(outState)
    }
}
