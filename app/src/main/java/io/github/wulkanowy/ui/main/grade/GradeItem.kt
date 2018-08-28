package io.github.wulkanowy.ui.main.grade

import android.view.View
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder
import io.github.wulkanowy.R
import io.github.wulkanowy.data.db.entities.Grade
import io.github.wulkanowy.utils.DATE_PATTERN
import io.github.wulkanowy.utils.getValueColor
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_grade.*
import org.apache.commons.lang3.time.DateFormatUtils.format

class GradeItem : AbstractFlexibleItem<GradeItem.ViewHolder>() {

    lateinit var grade: Grade

    private var valueColor = 0

    override fun createViewHolder(view: View, adapter: FlexibleAdapter<IFlexible<*>>): ViewHolder {
        return ViewHolder(view, adapter)
    }

    override fun getLayoutRes() = R.layout.item_grade

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GradeItem

        if (grade != other.grade) return false
        return true
    }

    override fun hashCode(): Int {
        return grade.hashCode()
    }

    override fun bindViewHolder(adapter: FlexibleAdapter<IFlexible<*>>, holder: ViewHolder,
                                position: Int, payloads: MutableList<Any>?) {
        if (valueColor == 0) valueColor = getValueColor(grade.value)
        val weightString = holder.containerView.resources.getString(R.string.grade_weight)

        holder.run {
            gradeItemValue.run {
                text = grade.value
                setBackgroundResource(valueColor)
            }
            gradeItemDescription.text = if (grade.description.isNotEmpty()) grade.description else grade.gradeSymbol
            gradeItemDate.text = format(grade.date, DATE_PATTERN)
            gradeItemWeight.text = "%s: %s".format(weightString, grade.weight)
        }
    }

    class ViewHolder(view: View, adapter: FlexibleAdapter<*>) : FlexibleViewHolder(view, adapter),
            LayoutContainer {

        override val containerView: View
            get() = contentView
    }
}
