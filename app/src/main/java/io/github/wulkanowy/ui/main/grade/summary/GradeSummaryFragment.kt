package io.github.wulkanowy.ui.main.grade.summary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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
        }

        gradeSummarySwipe.setOnRefreshListener { presenter.loadData(true) }
    }

    override fun updateDataSet(data: List<GradeSummaryItem>) {
        gradeSummaryAdapter.updateDataSet(data)
    }

    override fun showProgress(show: Boolean) {
        gradeSummaryProgress.visibility = if (show) VISIBLE else GONE
    }

    override fun showRefresh(show: Boolean) {
        gradeSummarySwipe.isRefreshing = show
    }

    override fun predictedString() = getString(R.string.grade_summary_predicted_average)

    override fun finalString() = getString(R.string.grade_summary_final_average)
}
