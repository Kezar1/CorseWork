import kotlinx.html.*
import kotlinx.html.table
import kotlinx.html.stream.*
import org.litote.kmongo.*
import java.io.File
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.*

private val crit = Controller::class.java.getResource("crit.txt")
    .readText()
    .split(" ", limit = 2)

class Controller {
    private val timeIntervals = listOf(
        "08:00 - 09:30",
        "09:45 - 11:15",
        "11:30 - 13:00",
        "13:55 - 15:25",
        "15:40 - 17:10"
    )
    val words = mapOf(
        "name" to database.find(Lesson::name eq crit[1]),
        "teacher" to database.find(Lesson::teacher eq crit[1]),
        "groups" to database.find(Lesson::groups contains crit[1]),
        "classroom" to database.find(Lesson::classroom eq crit[1]),
    )[crit[0]]

    val printableData = when(crit[0]) {
        "name" -> words?.toList()?.map { name ->
            PrintableLesson.make(name, Lesson::name) }
        "teacher" -> words?.toList()?.map { teacher ->
            PrintableLesson.make(teacher, Lesson::teacher) }
        "groups" -> words?.toList()?.map { groups ->
            PrintableLesson.make(groups, Lesson::groups) }
        "classroom" -> words?.toList()?.map { classroom ->
            PrintableLesson.make(classroom, Lesson::classroom) }
        else -> null
    }

    fun writeScheduleIntoFile() {
        val borderStyle = "border: 2px solid #095484;"
        val weekDays = DayOfWeek.values().dropLast(1)
        File("Schedule_${Date().time}.html").writeText(
            StringBuilder().appendHTML().html {
                body {
                    WeekType.values().forEach { weekType ->
                        br {
                            strong {
                                +"${weekType} / ${crit[1]}"
                            }
                            table {
                                style = borderStyle
                                tr {
                                    th { +""}
                                    weekDays.forEach { weekDay ->
                                        th {
                                            +weekDay.getDisplayName(TextStyle.FULL, Locale("ru","RU")) }
                                    }
                                }
                                timeIntervals.forEachIndexed { index,
                                                               timeInterval ->
                                    tr {
                                        td {
                                            +timeInterval
                                        }
                                        weekDays.forEach { weekDay ->
                                            val lesson =
                                                printableData?.firstOrNull { printable ->
                                                            printable.lesson.position.weekType == weekType &&
                                                            printable.lesson.position.dayOfWeek == weekDay &&
                                                            printable.lesson.position.timeInterval == timeInterval
                                                } ?: PrintableLesson.empty()

                                            td {
                                                style = borderStyle
                                                +lesson.meta
                                                br {
                                                    +lesson.fullName
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }.toString()
        )
    }
}