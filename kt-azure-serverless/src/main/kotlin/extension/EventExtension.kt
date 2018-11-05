package fr.xebicon.extension

import com.microsoft.azure.documentdb.*
import fr.xebicon.Event

val client = DocumentClient("https://kt-azure.documents.azure.com:443/",
  System.getenv("AZURE_COSMODB_KEY") ?: "",
  ConnectionPolicy.GetDefault(),
  ConsistencyLevel.Session)

val database: Database = client.queryDatabases("SELECT * FROM root r WHERE r.id='KtAzure'", null)
  .queryIterable.toList()[0]

val collection: DocumentCollection = client.queryCollections(database.selfLink, "SELECT * FROM root r WHERE r.id='Events'", null)
  .queryIterable.toList()[0]


fun Event.save(): Document {
  val eventDocument = Document(eventAdapter.toJson(this))
  return client.createDocument(collection.selfLink, eventDocument, null, false).resource
}
