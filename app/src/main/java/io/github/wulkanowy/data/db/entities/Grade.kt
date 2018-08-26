package io.github.wulkanowy.data.db.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "Grades")
data class Grade(

        @PrimaryKey(autoGenerate = true)
        var id: Long = 0,

        @ColumnInfo(name = "semester_id")
        var semesterId: String = "",

        @ColumnInfo(name = "student_id")
        var studentId: String = "",

        var subject: String,

        var value: String,

        var color: String,

        @ColumnInfo(name = "grade_symbol")
        var gradeSymbol: String = "",

        var description: String = "",

        var weight: String,

        var date: Date,

        var teacher: String
)
