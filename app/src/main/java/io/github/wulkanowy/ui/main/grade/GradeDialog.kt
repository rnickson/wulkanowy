package io.github.wulkanowy.ui.main.grade

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.wulkanowy.R
import io.github.wulkanowy.data.db.entities.Grade
import io.github.wulkanowy.utils.getColorName
import io.github.wulkanowy.utils.getValueColor
import kotlinx.android.synthetic.main.dialog_grade.*


class GradeDialog : DialogFragment() {

    private lateinit var grade: Grade

    companion object {
        private const val ARGUMENT_KEY = "Item"

        fun newInstance(grade: Grade): GradeDialog {
            return GradeDialog().apply {
                arguments = Bundle().apply { putSerializable(ARGUMENT_KEY, grade) }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.run {
            grade = getSerializable(ARGUMENT_KEY) as Grade
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_grade, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        gradeDialogSubject.text = grade.subject
        gradeDialogWeightValue.text = grade.weight
        gradeDialogDateValue.text = grade.date
        gradeDialogColorValue.text = getString(getColorName(grade.color))
        gradeDialogValue.run {
            text = grade.value
            setBackgroundResource(getValueColor(grade.value))
        }
        gradeDialogTeacherValue.text = if (grade.teacher.isEmpty()) {
            getString(R.string.all_no_data)
        } else grade.teacher
        gradeDialogDescriptionValue.text = grade.run {
            when {
                description.isEmpty() && gradeSymbol.isNotEmpty() -> gradeSymbol
                description.isEmpty() && gradeSymbol.isEmpty() -> getString(R.string.all_no_description)
                gradeSymbol.isNotEmpty() && description.isNotEmpty() -> "$gradeSymbol - $description"
                else -> description
            }
        }
        gradeDialogClose.setOnClickListener { dismiss() }
    }

}

