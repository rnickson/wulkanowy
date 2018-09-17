package io.github.wulkanowy.ui.main.grade

import android.os.Bundle
import android.view.*
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import io.github.wulkanowy.R
import io.github.wulkanowy.ui.base.BaseFragment
import io.github.wulkanowy.ui.base.BasePagerAdapter
import io.github.wulkanowy.ui.main.grade.details.GradeDetailsFragment
import io.github.wulkanowy.ui.main.grade.summary.GradeSummaryFragment
import kotlinx.android.synthetic.main.fragment_grade.*
import javax.inject.Inject

class GradeFragment : BaseFragment(), GradeView {

    @Inject
    lateinit var presenter: GradePresenter

    @Inject
    lateinit var pagerAdapter: BasePagerAdapter

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.attachView(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.action_menu_grade, menu)
    }

    override fun initView() {
        pagerAdapter.fragments.putAll(mapOf(
                getString(R.string.all_details) to GradeDetailsFragment.newInstance(),
                getString(R.string.grade_menu_summary) to GradeSummaryFragment.newInstance()
        ))
        gradeViewPager.adapter = pagerAdapter
        gradeTabLayout.setupWithViewPager(gradeViewPager)
    }

    override fun showContent(show: Boolean) {
        gradeViewPager.visibility = if (show) VISIBLE else INVISIBLE
        gradeTabLayout.visibility = if (show) VISIBLE else INVISIBLE
    }

    override fun showProgress(show: Boolean) {
        gradeProgress.visibility = if (show) VISIBLE else INVISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }
}