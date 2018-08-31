package io.github.wulkanowy.data.repositories.remote

import io.github.wulkanowy.api.Api
import io.github.wulkanowy.api.DATE_FORMAT
import io.github.wulkanowy.data.db.entities.Grade
import io.github.wulkanowy.data.db.entities.Semester
import io.reactivex.Single
import org.apache.commons.lang3.time.DateFormatUtils.format
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GradeRemote @Inject constructor(private val api: Api) {

    fun getGrades(semester: Semester): Single<List<Grade>> {
        return Single.just(api.run {
            if (diaryId != semester.diaryId) {
                diaryId = semester.diaryId
                notifyDataChanged()
            }
        }).flatMap { api.getGrades(semester.semesterId.toInt()) }
                .map { grades ->
                    grades.map {
                        Grade(
                                semesterId = semester.semesterId,
                                studentId = semester.studentId,
                                subject = it.subject,
                                value = it.value,
                                color = it.color,
                                gradeSymbol = it.symbol,
                                description = it.description,
                                weight = it.weight,
                                date = format(it.date, DATE_FORMAT),
                                teacher = it.teacher
                        )
                    }
                }
    }
}
