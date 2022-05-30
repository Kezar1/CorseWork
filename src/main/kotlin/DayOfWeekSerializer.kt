import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.DayOfWeek

object DayOfWeekSerializer : KSerializer<DayOfWeek> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("DayOfWeek", PrimitiveKind.STRING)
    override fun serialize(encoder: Encoder, value: DayOfWeek) =
        encoder.encodeString(
            when (value) {
                DayOfWeek.MONDAY -> "Пн"
                DayOfWeek.TUESDAY -> "Вт"
                DayOfWeek.WEDNESDAY -> "Ср"
                DayOfWeek.THURSDAY -> "Чт"
                DayOfWeek.FRIDAY -> "Пт"
                DayOfWeek.SATURDAY -> "Сб"
                DayOfWeek.SUNDAY -> "Вс"
            }
        )
    override fun deserialize(decoder: Decoder) =
        when (decoder.decodeString()) {
            "Пн" -> DayOfWeek.MONDAY
            "Вт" -> DayOfWeek.TUESDAY
            "Ср" -> DayOfWeek.WEDNESDAY
            "Чт" -> DayOfWeek.THURSDAY
            "Пт" -> DayOfWeek.FRIDAY
            "Сб" -> DayOfWeek.SATURDAY
            else -> DayOfWeek.SUNDAY
        }
}
