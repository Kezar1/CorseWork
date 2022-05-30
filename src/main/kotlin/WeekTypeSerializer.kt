import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
object WeekTypeSerializer : KSerializer<WeekType> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("WeekType", PrimitiveKind.STRING)
    override fun serialize(encoder: Encoder, value: WeekType) =
        encoder.encodeString(
            when (value) {
                WeekType.EVEN -> "Четная"
                WeekType.ODD -> "Нечетная"
            }
        )
    override fun deserialize(decoder: Decoder) =
        when (decoder.decodeString()) {
            "Четная" -> WeekType.EVEN
            else -> WeekType.ODD
        }
}
