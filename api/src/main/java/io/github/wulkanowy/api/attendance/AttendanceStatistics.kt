package io.github.wulkanowy.api.attendance

import io.github.wulkanowy.api.SnP
import io.github.wulkanowy.api.generic.Subject
import org.apache.commons.lang3.math.NumberUtils

class AttendanceStatistics(private val snp: SnP) {

    private val attendancePageUrl = "Frekwencja.mvc"

    fun getSubjectList(): List<Subject> {
        val mainContainer = snp.getSnPPageDocument(attendancePageUrl)
                .select(".mainContainer #idPrzedmiot").first()

        return mainContainer.select("option").map {
            Subject(it.attr("value").toInt(), it.text())
        }
    }

    fun getEntries() = getEntries(-1)

    fun getEntries(subjectId: Int): List<AttendanceType> {
        val mainContainer = snp.getSnPPageDocument("$attendancePageUrl?idPrzedmiot=$subjectId")
                .select(".mainContainer").first()

        val table = mainContainer.select("table").last()

        val headerCells = table.select("thead th").drop(1)

        return table.select("tbody tr").map {
            val monthCells = it.select("td")
            monthCells.drop(1).dropLast(1).mapIndexed { i, item ->
                AttendanceType(
                        name = monthCells[0].text(),
                        month = headerCells[i].text(),
                        value = NumberUtils.toInt(item.text(), 0)
                )
            }
        }.flatten().filter { it.value != 0 }
    }
}
