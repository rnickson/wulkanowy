package io.github.wulkanowy.data.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.github.wulkanowy.data.db.entities.Grade
import io.reactivex.Maybe

@Dao
interface GradeDao {

    @Insert
    fun insertAll(grades: List<Grade>)

    @Query("SELECT * FROM Grades WHERE semester_id = :semesterId AND student_id = :studentId")
    fun getGrades(semesterId: String, studentId: String): Maybe<List<Grade>>
}
