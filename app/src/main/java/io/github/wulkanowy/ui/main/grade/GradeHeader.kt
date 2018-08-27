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

class GradeHeader(private val subject: String,
                  private val average: String,
                  private val number: String) : AbstractExpandableItem<GradeHeader.ViewHolder, GradeItem>() {

    override fun createViewHolder(view: View?, adapter: FlexibleAdapter<IFlexible<*>>?): ViewHolder {
        return ViewHolder(view, adapter)
    }

    override fun bindViewHolder(adapter: FlexibleAdapter<IFlexible<*>>?, holder: ViewHolder?,
                                position: Int, payloads: MutableList<Any>?) {
        holder?.bind(subject, average, number)
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


    class ViewHolder(view: View?, adapter: FlexibleAdapter<IFlexible<*>>?) : ExpandableViewHolder(view, adapter),
            LayoutContainer {

        init {
            contentView.setOnClickListener(this)
        }

        fun bind(subject: String, average: String, number: String) {
            gradeHeaderSubject.text = subject
            gradeHeaderAverage.text = average
            gradeHeaderNumber.text = number
            gradeHeaderFinal.visibility = GONE
            gradeHeaderPredicted.visibility = GONE
        }

        override fun shouldNotifyParentOnClick() = true

        override val containerView: View?
            get() = contentView
    }
}

