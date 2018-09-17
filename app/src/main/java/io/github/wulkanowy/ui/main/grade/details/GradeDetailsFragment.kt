package io.github.wulkanowy.ui.main.grade.details

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.common.SmoothScrollLinearLayoutManager
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import io.github.wulkanowy.R
import io.github.wulkanowy.data.db.entities.Grade
import io.github.wulkanowy.ui.base.BaseFragment
import io.github.wulkanowy.utils.extension.setOnItemClickListener
import io.github.wulkanowy.utils.extension.setOnUpdateListener
import kotlinx.android.synthetic.main.fragment_grade_details.*
import javax.inject.Inject

class GradeDetailsFragment : BaseFragment(), GradeDetailsView {

    @Inject
    lateinit var presenter: GradeDetailsPresenter

    @Inject
    lateinit var gradeAdapter: FlexibleAdapter<AbstractFlexibleItem<*>>

    companion object {
        fun newInstance() = GradeDetailsFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_grade_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.run {
            attachView(this@GradeDetailsFragment)
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
        gradeDetailsRecycler.run {
            layoutManager = SmoothScrollLinearLayoutManager(context)
            adapter = gradeAdapter
        }
        gradeDetailsSwipe.setOnRefreshListener { presenter.loadData(forceRefresh = true) }
    }

    override fun updateData(data: List<GradeDetailsHeader>) {
        gradeAdapter.updateDataSet(data, true)
    }

    override fun showEmpty(show: Boolean) {
        gradeDetailsEmpty.visibility = if (show) VISIBLE else GONE
    }

    override fun showProgress(show: Boolean) {
        gradeDetailsProgress.visibility = if (show) VISIBLE else GONE
    }

    override fun showContent(show: Boolean) {
        gradeDetailsRecycler.visibility = if (show) VISIBLE else INVISIBLE
    }

    override fun showRefresh(show: Boolean) {
        gradeDetailsSwipe.isRefreshing = show
    }

    override fun showGradeDialog(grade: Grade) {
        GradeDetailsDialog.newInstance(grade).show(fragmentManager, grade.toString())
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

    override fun emptyAverageString(): String = getString(R.string.grade_no_average)

    override fun averageString(): String = getString(R.string.grade_average)

    override fun gradeNumberString(number: Int): String = resources.getQuantityString(R.plurals.grade_number_item, number, number)

    override fun weightString(): String = getString(R.string.grade_weight)

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }
}
