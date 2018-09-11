package io.github.wulkanowy.ui.main.grade

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.*
import android.view.View.*
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.common.SmoothScrollLinearLayoutManager
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import io.github.wulkanowy.R
import io.github.wulkanowy.data.db.entities.Grade
import io.github.wulkanowy.ui.base.BaseFragment
import io.github.wulkanowy.ui.main.FragmentNavigationListener
import io.github.wulkanowy.ui.main.grade.summary.GradeSummaryFragment
import io.github.wulkanowy.utils.extension.setOnItemClickListener
import io.github.wulkanowy.utils.extension.setOnUpdateListener
import kotlinx.android.synthetic.main.fragment_grade.*
import javax.inject.Inject

class GradeFragment : BaseFragment(), GradeView {

    @Inject
    lateinit var presenter: GradePresenter

    @Inject
    lateinit var gradeAdapter: FlexibleAdapter<AbstractFlexibleItem<*>>

    companion object {
        fun newInstance() = GradeFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_grade, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.action_menu_grade, menu)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.run {
            attachView(this@GradeFragment)
            loadData()
        }
    }

    override fun initView() {
        gradeAdapter.run {
            isAutoCollapseOnExpand = true
            isAutoScrollOnExpand = true
            setOnUpdateListener { presenter.onUpdateDataList(it) }
            setOnItemClickListener { presenter.onGradeItemSelected(getItem(it)) }
        }
        gradeRecycler.run {
            layoutManager = SmoothScrollLinearLayoutManager(context)
            adapter = gradeAdapter
        }
        gradeSwipe.setOnRefreshListener { presenter.loadData(forceRefresh = true) }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.gradeMenuSemester -> presenter.onSemesterSwitchSelected()
            R.id.gradeMenuSummary -> presenter.onSummarySwitchSelected()
            else -> false
        }
    }

    override fun updateData(data: List<GradeHeader>) {
        gradeAdapter.updateDataSet(data, true)
    }

    override fun showEmpty(show: Boolean) {
        gradeEmpty.visibility = if (show) VISIBLE else GONE
    }

    override fun showProgress(show: Boolean) {
        gradeProgress.visibility = if (show) VISIBLE else GONE
    }

    override fun showContent(show: Boolean) {
        gradeRecycler.visibility = if (show) VISIBLE else INVISIBLE
    }

    override fun showRefresh(show: Boolean) {
        gradeSwipe.isRefreshing = show
    }

    override fun showGradeDialog(grade: Grade) {
        GradeDialog.newInstance(grade).show(fragmentManager, grade.toString())
    }

    override fun showSemesterDialog(selectedIndex: Int) {
        val semesters = arrayOf(getString(R.string.grade_semester, 1),
                getString(R.string.grade_semester, 2))

        context?.let {
            AlertDialog.Builder(it)
                    .setSingleChoiceItems(semesters, selectedIndex) { dialog, which ->
                        presenter.onSemesterSelected(which)
                        dialog.dismiss()
                    }
                    .setTitle(R.string.grade_switch_semester)
                    .setNegativeButton(R.string.all_cancel) { dialog, _ -> dialog.dismiss() }
                    .show()
        }
    }

    override fun openSummaryView() {
        (activity as? FragmentNavigationListener)?.pushFragment(GradeSummaryFragment.newInstance())
    }

    override fun emptyAverageString(): String = getString(R.string.grade_no_average)

    override fun averageString(): String = getString(R.string.grade_average)

    override fun gradeNumberString(number: Int): String = resources.getQuantityString(R.plurals.grade_number_item, number, number)

    override fun weightString(): String = getString(R.string.grade_weight)

}
