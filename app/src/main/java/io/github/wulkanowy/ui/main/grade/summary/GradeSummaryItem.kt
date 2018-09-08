package io.github.wulkanowy.ui.main.grade.summary

import android.view.View
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractSectionableItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder
import io.github.wulkanowy.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_grade_summary.*

class GradeSummaryItem(header: GradeSummaryHeader) :
        AbstractSectionableItem<GradeSummaryItem.ViewHolder, GradeSummaryHeader>(header) {

    lateinit var grade: String

    lateinit var title: String

    override fun getLayoutRes() = R.layout.item_grade_summary

    override fun createViewHolder(view: View?, adapter: FlexibleAdapter<IFlexible<*>>?): ViewHolder {
        return ViewHolder(view, adapter)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GradeSummaryItem

        if (grade != other.grade) return false
        if (title != other.title) return false

        return true
    }

    override fun hashCode(): Int {
        var result = grade.hashCode()
        result = 31 * result + title.hashCode()
        return result
    }

    override fun bindViewHolder(adapter: FlexibleAdapter<IFlexible<*>>?, holder: ViewHolder?,
                                position: Int, payloads: MutableList<Any>?) {
        holder?.run {
            gradeSummaryItemGrade.text = grade
            gradeSummaryItemTitle.text = title
        }
    }

    class ViewHolder(view: View?, adapter: FlexibleAdapter<IFlexible<*>>?)
        : FlexibleViewHolder(view, adapter), LayoutContainer {

        override val containerView: View?
            get() = contentView
    }
}
