package io.github.wulkanowy.data.db.converter

import android.arch.persistence.room.TypeConverter
import java.util.*

class DateConverter {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? = value?.run { Date(value) }


    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = date?.time
}

