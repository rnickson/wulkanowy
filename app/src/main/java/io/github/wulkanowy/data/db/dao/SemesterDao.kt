package io.github.wulkanowy.data.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import io.github.wulkanowy.data.db.entities.Semester
import io.reactivex.Single

@Dao
interface SemesterDao {

    @Insert(onConflict = REPLACE)
    fun insertAll(semester: List<Semester>)

    @Query("SELECT * FROM Semesters WHERE student_id = :studentId AND is_current = 1")
    fun getCurrentSemester(studentId: String): Single<Semester>
}
