package io.github.wulkanowy.data.repositories

import com.github.pwittchen.reactivenetwork.library.rx2.internet.observing.InternetObservingSettings
import io.github.wulkanowy.data.db.entities.Semester
import io.github.wulkanowy.data.repositories.local.GradeLocal
import io.github.wulkanowy.data.repositories.remote.GradeRemote
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GradeRepository @Inject constructor(
        settings: InternetObservingSettings,
        private val local: GradeLocal,
        private val remote: GradeRemote
) {

    fun getGrades(semester: Semester, forceRefresh: Boolean) {

    }
}

