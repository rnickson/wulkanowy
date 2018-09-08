package io.github.wulkanowy.ui.main.exam

import android.support.v4.app.Fragment
import com.stepstone.stepper.Step
import com.stepstone.stepper.VerificationError
import android.os.Bundle
import android.support.annotation.NonNull
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import io.github.wulkanowy.R

class ExamWeekFragment : Fragment(), Step {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        //initialize your UI

        return inflater.inflate(R.layout.fragment_exam_tab, container, false)
    }

    override fun verifyStep(): VerificationError? {
        //return null if the user can go to the next step, create a new VerificationError instance otherwise
        return null
    }

    override fun onSelected() {
        //update UI when selected
    }

    override fun onError(@NonNull error: VerificationError) {
        //handle error inside of the fragment, e.g. show error on EditText
    }
}
