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

class GradeItem(private val grade: Grade, private val weightString: String)
    : AbstractFlexibleItem<GradeItem.ViewHolder>() {

    override fun createViewHolder(view: View?, adapter: FlexibleAdapter<IFlexible<*>>?): ViewHolder {
        return ViewHolder(view, adapter)
    }

    override fun bindViewHolder(adapter: FlexibleAdapter<IFlexible<*>>?, holder: ViewHolder?,
                                position: Int, payloads: MutableList<Any>?) {
        holder?.bind(grade, weightString)
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

    class ViewHolder(view: View?, adapter: FlexibleAdapter<*>?) : FlexibleViewHolder(view, adapter),
            LayoutContainer {

        private var valueColor = 0

        fun bind(item: Grade, weightString: String) {
            if (valueColor == 0) valueColor = getValueColor(item.value)

            gradeItemValue.run {
                text = item.value
                setBackgroundResource(valueColor)
            }
            gradeItemDescription.text = if (item.description.isNotEmpty()) item.description else item.gradeSymbol
            gradeItemDate.text = format(item.date, DATE_PATTERN)
            gradeItemWeight.text = "%s: %s".format(weightString, item.weight)
        }

        override val containerView: View?
            get() = contentView
    }
}
