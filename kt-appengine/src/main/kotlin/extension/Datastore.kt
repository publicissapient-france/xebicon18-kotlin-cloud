package fr.xebicon.extension

import com.google.cloud.datastore.Datastore
import com.google.cloud.datastore.FullEntity
import com.google.cloud.datastore.IncompleteKey
import fr.xebicon.Event

fun Event.build(service: Datastore): FullEntity<IncompleteKey> {
  val key = service.newKeyFactory().setKind("events").newKey()
  return FullEntity.newBuilder(key)
    .set("title", title)
    .build()
}
