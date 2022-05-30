import kotlinx.serialization.Serializable
import java.time.DayOfWeek

@Serializable
data class Lesson(
    val type: LessonType,
    val position: LessonPosition,
    val name: String,
    val teacher: String,
    val classroom: String,
    val groups: List<String>,
    val subgroup: String? = null
) {
    fun fullName(): String {
        return "$type $name"
    }
}

@Serializable
enum class LessonType(private val rawValue: String) {
    LECTURE("Лек."),
    PRACTICE("Пр."),
    LAB("Лаб."),
    KSR("КСР"),
    KRB("КРБ"),
    NONE("");
    override fun toString() = rawValue
}

@Serializable
data class LessonPosition(
    @Serializable(with = WeekTypeSerializer::class)
    val weekType: WeekType,
    @Serializable(with = DayOfWeekSerializer::class)
    val dayOfWeek: DayOfWeek,
    val timeInterval: String,
)

@Serializable
enum class WeekType {
    ODD, EVEN;
    override fun toString(): String {
        return when (this) {
            ODD -> "Нечетная неделя"
            EVEN -> "Четная неделя"
        }
    }
}
