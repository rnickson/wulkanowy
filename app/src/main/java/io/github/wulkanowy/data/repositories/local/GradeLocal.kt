package io.github.wulkanowy.data.repositories.local

import io.github.wulkanowy.data.db.dao.GradeDao
import io.github.wulkanowy.data.db.entities.Grade
import io.github.wulkanowy.data.db.entities.Semester
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GradeLocal @Inject constructor(private val gradeDb: GradeDao) {

    fun getGrades(semester: Semester): Single<List<Grade>> {
        return gradeDb.getGrades(semester.semesterId, semester.studentId)
    }

}
