import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.document.Item
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

data class Event(val title: String, val description: String)

@Suppress("unused")
class SaveEvent : RequestHandler<Map<String, Any>, Unit> {

  companion object {
    const val BODY = "body"
    const val EVENTS = "events"
  }

  private val mapper = jacksonObjectMapper()

  override fun handleRequest(input: Map<String, Any>, context: Context) {
    val event = mapper.readValue<Event>(input[BODY] as String)
    val client = AmazonDynamoDBClientBuilder.standard().build()
    val db = DynamoDB(client)
    val table = db.getTable(EVENTS)
    table.putItem(event.build())
    println("Event processed")
  }
}

private fun Event.build(): Item =
  Item().withPrimaryKey("title", title).withString("description", description)
