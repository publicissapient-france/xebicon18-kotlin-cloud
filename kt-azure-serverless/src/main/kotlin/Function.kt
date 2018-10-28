import com.microsoft.azure.documentdb.*
import com.microsoft.azure.functions.ExecutionContext
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

const val KEY: String = ""

data class Event(val title: String)

val moshi: Moshi = Moshi.Builder()
  .add(KotlinJsonAdapterFactory())
  .build()

val eventAdapter: JsonAdapter<Event> = moshi.adapter(Event::class.java)

val client = DocumentClient("https://kt-azure.documents.azure.com:443/",
  KEY,
  ConnectionPolicy.GetDefault(),
  ConsistencyLevel.Session)

val database: Database = client.queryDatabases("SELECT * FROM root r WHERE r.id='KtAzure'", null)
  .queryIterable.toList()[0]

val collection: DocumentCollection = client.queryCollections(database.selfLink, "SELECT * FROM root r WHERE r.id='Events'", null)
  .queryIterable.toList()[0]

@Suppress("unused") // Used by Azure
fun saveEvent(data: String, context: ExecutionContext): String {
  context.logger.info("saveEvent called with $data")
  val eventDocument = Document(data)
  client.createDocument(collection.selfLink, eventDocument, null, false).resource
  val event = eventAdapter.fromJson(data)
  return event?.title ?: data
}
