import java.time.DayOfWeek
import kotlin.reflect.KProperty1

class PrintableLesson(
    val lesson: Lesson,
    val meta: String,
    val fullName: String
) {
    companion object Factory {
        fun empty(): PrintableLesson = PrintableLesson(Lesson(
                    LessonType.NONE,
            LessonPosition(WeekType.EVEN, DayOfWeek.MONDAY, ""),
            "",
            "",
            "",
            listOf("")
        ), "", "")

        fun <T> make(lesson: Lesson, excludingPath: KProperty1<Lesson, T>):
                PrintableLesson {
            return when (excludingPath) {
                Lesson::teacher -> {
                    val meta = "${lesson.groups.joinToString(";")}.${lesson.classroom}"
                    PrintableLesson(lesson, meta, lesson.fullName())
                }
                Lesson::name -> {
                    val meta = "${lesson.type}${lesson.groups.joinToString(";")}${lesson.classroom} ${lesson.teacher}"
                    PrintableLesson(lesson, meta, "")
                }
                Lesson::groups -> {
                    val meta = "а.${lesson.classroom} ${lesson.teacher}"
                    PrintableLesson(lesson, meta, lesson.fullName())
                }
                Lesson::classroom -> {
                    val meta = "${lesson.groups.joinToString(";")}${lesson.teacher}"
                    PrintableLesson(lesson, meta, lesson.fullName())
                }
                else -> empty()
            }
        }
    }
}
