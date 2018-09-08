package io.github.wulkanowy.ui.main.exam

import android.content.Context
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.annotation.IntRange
import android.support.v4.app.FragmentManager
import com.stepstone.stepper.Step
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter
import com.stepstone.stepper.viewmodel.StepViewModel

class ExamWeekAdapter(fm: FragmentManager, context: Context) : AbstractFragmentStepAdapter(fm, context) {

    companion object {
        private const val CURRENT_STEP_POSITION_KEY = "current_step"
    }

    override fun createStep(position: Int): Step {
        println(position)
        return ExamWeekFragment().apply {
            arguments = Bundle().apply {
                putInt(CURRENT_STEP_POSITION_KEY, position)
            }
        }
    }

    override fun getCount(): Int {
        return 56
    }

    @NonNull
    override fun getViewModel(@IntRange(from = 0) position: Int): StepViewModel {
        //Override this method to set Step title for the Tabs, not necessary for other stepper types
        return StepViewModel.Builder(context)
                .setTitle("Week $position")
                .create()
    }
}
