package io.github.wulkanowy.ui.main.exam

import android.support.v7.widget.RecyclerView
import android.view.View
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder
import io.github.wulkanowy.R
import io.github.wulkanowy.data.db.entities.Exam
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_exam.*

class ExamItem : AbstractFlexibleItem<ExamItem.ViewHolder>() {

    lateinit var exam: Exam

    override fun equals(other: Any?): Boolean {
        return true
    }

    override fun createViewHolder(view: View, adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>): ViewHolder {
        return ViewHolder(view, adapter)
    }

    override fun hashCode(): Int {
        return exam.hashCode()
    }

    override fun getLayoutRes() = R.layout.item_exam

    override fun bindViewHolder(adapter: FlexibleAdapter<IFlexible<*>>, holder: ViewHolder,
                                position: Int, payloads: MutableList<Any>?) {
        holder.run {
            examItemSubject.text = exam.subject
            examItemTeacher.text = exam.teacher
            examItemType.text = exam.type
        }
    }

    class ViewHolder(view: View, adapter: FlexibleAdapter<*>) : FlexibleViewHolder(view, adapter),
            LayoutContainer {

        override val containerView: View
            get() = contentView
    }
}
