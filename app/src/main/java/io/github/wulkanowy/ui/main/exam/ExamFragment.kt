package io.github.wulkanowy.ui.main.exam

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.wulkanowy.R
import io.github.wulkanowy.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_exam.*
import javax.inject.Inject

class ExamFragment : BaseFragment(), ExamView {

    @Inject
    lateinit var presenter: ExamPresenter

    companion object {
        fun newInstance() = ExamFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_exam, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.attachView(this)
    }

    override fun initView() {
        stepperLayout.adapter = ExamWeekAdapter(fragmentManager!!, context!!)
    }
}
