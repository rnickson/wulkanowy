package io.github.wulkanowy.ui.main.grade.summary

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.common.SmoothScrollLinearLayoutManager
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import io.github.wulkanowy.R
import io.github.wulkanowy.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_grade_summary.*
import javax.inject.Inject

class GradeSummaryFragment : BaseFragment(), GradeSummaryView {

    @Inject
    lateinit var presenter: GradeSummaryPresenter

    @Inject
    lateinit var gradeSummaryAdapter: FlexibleAdapter<AbstractFlexibleItem<*>>

    companion object {
        fun newInstance() = GradeSummaryFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        return inflater.inflate(R.layout.fragment_grade_summary, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.run {
            attachView(this@GradeSummaryFragment)
            loadData()
        }
    }

    override fun initView() {
        gradeSummaryAdapter.setDisplayHeadersAtStartUp(true)

        gradeSummaryRecycler.run {
            layoutManager = SmoothScrollLinearLayoutManager(context)
            adapter = gradeSummaryAdapter
            isNestedScrollingEnabled = false
        }
        gradeSummarySwipe.setOnRefreshListener { presenter.loadData(true) }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> presenter.onBackPressed()
            else -> false
        }
    }

    override fun updateDataSet(data: List<GradeSummaryItem>, finalAvg: String, calculatedAvg: String) {
        gradeSummaryAdapter.updateDataSet(data)
        gradeSummaryFinalAverage.text = finalAvg
        gradeSummaryCalculatedAverage.text = calculatedAvg
    }

    override fun popView() {
        (activity as? AppCompatActivity)?.onBackPressed()
    }

    override fun showContent(show: Boolean) {
        gradeSummaryContent.visibility = if (show) VISIBLE else INVISIBLE
    }

    override fun showProgress(show: Boolean) {
        gradeSummaryProgress.visibility = if (show) VISIBLE else GONE
    }

    override fun showRefresh(show: Boolean) {
        gradeSummarySwipe.isRefreshing = show
    }

    override fun showEmpty(show: Boolean) {
        gradeSummaryEmpty.visibility = if (show) VISIBLE else GONE
    }

    override fun predictedString() = getString(R.string.grade_summary_predicted_average)

    override fun finalString() = getString(R.string.grade_summary_final_average)
}
