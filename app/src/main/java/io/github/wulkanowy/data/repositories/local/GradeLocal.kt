package io.github.wulkanowy.data.repositories.local

import io.github.wulkanowy.data.db.dao.GradeDao
import io.github.wulkanowy.data.db.entities.Grade
import io.github.wulkanowy.data.db.entities.Semester
import io.reactivex.Maybe
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GradeLocal @Inject constructor(private val gradeDb: GradeDao) {

    fun getGrades(semester: Semester): Maybe<List<Grade>> {
        return gradeDb.getGrades(semester.semesterId, semester.studentId).filter { !it.isEmpty() }
    }

    fun saveGrades(grades: List<Grade>) {
        gradeDb.insertAll(grades)
    }

    fun deleteGrades(grades: List<Grade>) {
        gradeDb.deleteAll(grades)
    }
}
