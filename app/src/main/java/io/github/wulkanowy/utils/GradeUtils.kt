package io.github.wulkanowy.utils

import io.github.wulkanowy.R
import io.github.wulkanowy.data.db.entities.Grade

private val validGrade = "^(\\++|-|--|=)?[0-6](\\++|-|--|=)?$".toRegex()

fun calcAverage(gradeList: List<Grade>): Float {
    var counter = 0f
    var denominator = 0f

    gradeList.forEach {
        if (it.value.matches(validGrade)) {
            val weight = it.weight.dropLast(3).toFloat()

            counter += getCalculatedValue(it.value) * weight
            denominator += weight
        }
    }
    return if (counter == 0f) counter else counter / denominator
}

private fun getCalculatedValue(value: String): Float {
    return value.run {
        when {
            matches("[-][0-6]|[0-6][-]".toRegex()) -> replace("-", "").toFloat() - 0.33f
            matches("[+][0-6]|[0-6][+]".toRegex()) -> replace("+", "").toFloat() + 0.33f
            matches("[-|=]{1,2}[0-6]|[0-6][-|=]{1,2}".toRegex()) -> replace("[-|=]{1,2}".toRegex(), "").toFloat() - 0.5f
            else -> toFloat()
        }
    }
}

fun getValueColor(value: String): Int {
    return if (!value.contains(validGrade)) {
        R.color.grade_default
    } else {
        when (value.replace("[^0-6]".toRegex(), "").toInt()) {
            6 -> R.color.grade_six
            5 -> R.color.grade_five
            4 -> R.color.grade_four
            3 -> R.color.grade_three
            2 -> R.color.grade_two
            1 -> R.color.grade_one
            else -> R.color.grade_default
        }
    }

}

