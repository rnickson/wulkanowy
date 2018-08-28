package io.github.wulkanowy.ui.main.grade

import android.view.View
import android.view.View.GONE
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractExpandableItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.ExpandableViewHolder
import io.github.wulkanowy.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.header_grade.*

class GradeHeader : AbstractExpandableItem<GradeHeader.ViewHolder, GradeItem>() {

    lateinit var subject: String

    var number = 0

    var average = 0f

    override fun createViewHolder(view: View?, adapter: FlexibleAdapter<IFlexible<*>>?): ViewHolder {
        return ViewHolder(view, adapter)
    }

    override fun getLayoutRes() = R.layout.header_grade

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GradeHeader

        if (subject != other.subject) return false

        return true
    }

    override fun hashCode(): Int {
        return subject.hashCode()
    }

    override fun bindViewHolder(adapter: FlexibleAdapter<IFlexible<*>>?, holder: ViewHolder,
                                position: Int, payloads: MutableList<Any>?) {
        val res = holder.containerView.resources

        holder.run {
            gradeHeaderSubject.text = subject
            gradeHeaderAverage.text = res.run {
                if (average == 0f) getString(R.string.grade_no_average)
                else getString(R.string.grade_average, average)
            }
            gradeHeaderNumber.text = res.getQuantityString(R.plurals.grade_number_item, number, number)

            gradeHeaderPredicted.visibility = GONE
            gradeHeaderFinal.visibility = GONE
        }
    }

    class ViewHolder(view: View?, adapter: FlexibleAdapter<IFlexible<*>>?) : ExpandableViewHolder(view, adapter),
            LayoutContainer {

        init {
            contentView.setOnClickListener(this)
        }

        override fun shouldNotifyParentOnClick() = true

        override val containerView: View
            get() = contentView
    }
}
