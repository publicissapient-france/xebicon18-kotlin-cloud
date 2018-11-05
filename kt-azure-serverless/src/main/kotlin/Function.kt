package fr.xebicon

import com.microsoft.azure.functions.ExecutionContext
import fr.xebicon.extension.save
import fr.xebicon.extension.toEvent

data class Event(val title: String)

@Suppress("unused") // Used by Azure
fun saveEvent(data: String, context: ExecutionContext): String {
  context.logger.info("saveEvent called with $data")
  val event = data.toEvent()
  event?.save()
  return event?.title ?: "unknown"
}
