package fr.xebicon.extension

import com.google.cloud.datastore.Datastore
import com.google.cloud.datastore.DatastoreOptions
import com.google.cloud.datastore.Entity
import com.google.cloud.datastore.FullEntity
import fr.xebicon.Event

private val service: Datastore = DatastoreOptions.getDefaultInstance().service

fun Event.save(): Entity? {
  val key = service.newKeyFactory().setKind("events").newKey()
  val entity = FullEntity.newBuilder(key)
    .set("title", title)
    .build()
  return service.add(entity)
}
