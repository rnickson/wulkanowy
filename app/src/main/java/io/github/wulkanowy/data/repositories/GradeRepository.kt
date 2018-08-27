package io.github.wulkanowy.data.repositories

import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.github.pwittchen.reactivenetwork.library.rx2.internet.observing.InternetObservingSettings
import io.github.wulkanowy.data.db.entities.Grade
import io.github.wulkanowy.data.db.entities.Semester
import io.github.wulkanowy.data.repositories.local.GradeLocal
import io.github.wulkanowy.data.repositories.remote.GradeRemote
import io.reactivex.Single
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GradeRepository @Inject constructor(
        private val settings: InternetObservingSettings,
        private val local: GradeLocal,
        private val remote: GradeRemote
) {

    fun getGrades(semester: Semester): Single<List<Grade>> {
        return local.getGrades(semester).switchIfEmpty(
                ReactiveNetwork.checkInternetConnectivity(settings)
                        .flatMap {
                            if (it) remote.getGrades(semester)
                            else Single.error(UnknownHostException())
                        }.doOnSuccess { local.saveGrades(it) })

    }
}
