package fr.xebicon.extension

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.document.Item
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome
import com.amazonaws.services.dynamodbv2.document.Table
import fr.xebicon.Event

const val EVENTS = "events"

val client: AmazonDynamoDB = AmazonDynamoDBClientBuilder.standard().build()
val db = DynamoDB(client)
val table: Table = db.getTable(EVENTS)

fun Event.save(): PutItemOutcome? =
  table.putItem(Item().withPrimaryKey("title", title).withString("description", description))
