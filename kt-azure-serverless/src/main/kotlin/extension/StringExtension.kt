package fr.xebicon.extension

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import fr.xebicon.Event

val moshi: Moshi = Moshi.Builder()
  .add(KotlinJsonAdapterFactory())
  .build()

val eventAdapter: JsonAdapter<Event> = moshi.adapter(Event::class.java)

fun String.toEvent(): Event? = eventAdapter.fromJson(this)
