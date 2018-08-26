package io.github.wulkanowy.data.repositories.local

import io.github.wulkanowy.data.db.dao.GradeDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GradeLocal @Inject constructor(private val gradeDb: GradeDao) {

}

