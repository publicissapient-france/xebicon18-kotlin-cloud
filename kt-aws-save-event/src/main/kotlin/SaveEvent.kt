package fr.xebicon

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import fr.xebicon.extension.save

data class Event(val title: String, val description: String)

@Suppress("unused")
class SaveEvent : RequestHandler<Map<String, Any>, Unit> {

  companion object {
    const val BODY = "body"
  }

  private val mapper = jacksonObjectMapper()

  override fun handleRequest(input: Map<String, Any>, context: Context) {
    val event = mapper.readValue<Event>(input[BODY] as String)
    event.save()
  }
}
