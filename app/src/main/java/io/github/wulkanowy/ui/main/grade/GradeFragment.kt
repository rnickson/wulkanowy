package io.github.wulkanowy.ui.main.grade

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
import io.github.wulkanowy.utils.extension.setOnItemClickListener
import io.github.wulkanowy.utils.extension.setOnUpdateListener
import kotlinx.android.synthetic.main.fragment_grade.*
import javax.inject.Inject

class GradeFragment : BaseFragment(), GradeView {

    @Inject
    lateinit var presenter: GradePresenter

    private val gradeAdapter = FlexibleAdapter<AbstractFlexibleItem<*>>(null)

    companion object {
        fun newInstance() = GradeFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_grade, container, false)
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
            setOnItemClickListener { position ->
                getItem(position).let {
                    if (it is GradeItem) {
                        GradeDialog.newInstance(it.grade).show(fragmentManager, it.toString())
                    }
                }
            }
        }
        gradeRecycler.run {
            layoutManager = SmoothScrollLinearLayoutManager(context)
            adapter = gradeAdapter
        }
        gradeSwipe.setOnRefreshListener { presenter.loadData(forceRefresh = true) }
    }

    override fun updateData(data: List<GradeHeader>) {
        gradeAdapter.updateDataSet(data, true)
    }

    override fun isViewEmpty(): Boolean = gradeAdapter.isEmpty

    override fun showEmptyView(show: Boolean) {
        gradeEmpty.visibility = if (show) VISIBLE else GONE
    }

    override fun showProgress(show: Boolean) {
        gradeProgress.visibility = if (show) VISIBLE else GONE
    }

    override fun setRefresh(show: Boolean) {
        gradeSwipe.isRefreshing = show
    }

    override fun emptyAverageString(): String = getString(R.string.grade_no_average)

    override fun averageString(): String = getString(R.string.grade_average)

    override fun gradeNumberString(number: Int): String = resources.getQuantityString(R.plurals.grade_number_item, number, number)

    override fun weightString(): String = getString(R.string.grade_weight)

}
