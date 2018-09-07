package io.github.wulkanowy.data.repositories.remote

import io.github.wulkanowy.api.Api
import io.github.wulkanowy.data.db.entities.Exam
import io.github.wulkanowy.data.db.entities.Semester
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class ExamRemote @Inject constructor(private val api: Api) {

    fun getExams(semester: Semester, startDate: Date): Single<List<Exam>> {
        return Single.just(api.run {
            if (diaryId != semester.diaryId) {
                diaryId = semester.diaryId
                notifyDataChanged()
            }
        }).flatMap { api.getExams(startDate) }
                .map { exams ->
                    exams.map {
                        Exam(
                                studentId = semester.studentId,
                                diaryId = semester.diaryId,
                                date = it.date,
                                entryDate = it.entryDate,
                                subject = it.subject,
                                group = it.group,
                                type = it.type,
                                description = it.description,
                                teacher = it.teacher,
                                teacherSymbol = it.teacherSymbol
                        )
                    }
                }
    }
}
