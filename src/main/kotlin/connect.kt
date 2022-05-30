import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection

val client =
    KMongo.createClient("mongodb://root:JT4BxmV4k1e2@192.168.0.112:27017")
val mongoDatabase = client.getDatabase("test")
val database = mongoDatabase.getCollection<Lesson>()
