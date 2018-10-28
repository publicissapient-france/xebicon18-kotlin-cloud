theme: Fira, 6
slidenumbers: true

![](https://xebicon.fr/wp-content/uploads/2018/06/Xebicon18-brongniart-tech4exec.jpg)

## XebiCon'__18__

# Kotlin in your __Cloud__

---

# Who we are?

![left filtered](blacroix.jpg)

Benjamin Lacroix

@benjlacroix

Fullstack Developer
& Manager

---

# Who we are?

![left filtered](blacroix.jpg)

Paul-Guillaume

Fullstack Developer
& Manager

---

# Agenda

- Kotlin?
- Appengine on GCP
- Lambda on AWS
- Function on Azure

---

# Whose for?

- Android developer wanting to build backend apps
- Backend developer thinking of switching to Kotlin

^ Partager du code
Utiliser des bibliothèques connues

---

# Why?

- I am an Android developer

- Kotlin already on Android

- Kotlin is better than Java

- How to go From Android to backend

- Let's try GCP

---

# What?

- Statically typed programming language that targets JVM, Android, Javascript and Native

- Developed by JetBrains

- Established in 2011 and open-sourced early on

- version 1.0 released in 2016

---

# Google Cloud Platform

- Java 8

- Gradle

- Kotlin

- Ktor

---

> Easy to use, fun and asynchronous.
-- ktor.io

---

# Requirements

- Google Cloud CLI
- GCloud App Engine Java
- GCloud Github repository for 
Ktor and AppEngine

---

# build.gradle

[.code-highlight: none]
[.code-highlight: 4, 9, 18]
[.code-highlight: 14, 19-20]
[.code-highlight: 5, 10-11, 21]

```gradle
buildscript {
  repositories { ... }
  dependencies {
    classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
    classpath "com.google.cloud.tools:appengine-gradle-plugin:$appengine_plugin_version"
  }
}

apply plugin: 'kotlin'
apply plugin: 'war'
apply plugin: 'com.google.cloud.tools.appengine'

repositories {
  maven { url "https://kotlin.bintray.com/ktor" }
}

dependencies {
  compile "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version"
  compile "io.ktor:ktor-server-servlet:$ktor_version"
  compile "io.ktor:ktor-gson:$ktor_version"
  providedCompile "com.google.appengine:appengine:$appengine_version"
}
```

---

# application.conf

```
ktor {
  application {
    modules = [fr.xebicon.AppKt.main]
  }
}
```

---

# Models

[.code-highlight: none]
[.code-highlight: 1]
[.code-highlight: 3-6]
[.code-highlight: 8]
[.code-highlight: 10-14]

```kotlin
data class Speaker(val name: String)

data class Event(val title: String,
  val description: String,
  val speakers: List<Speaker>,
  val date: LocalDate)

val john = Speaker("John")

val events = listOf(
  Event("Keynote",
    "Amazing Keynote from an amazing speaker",
    listOf(john),
    LocalDate.of(2018, 11, 20))
)
```

---

# App.kt

[.code-highlight: none]
[.code-highlight: 1]
[.code-highlight: 2-4]
[.code-highlight: 5]
[.code-highlight: 6]
[.code-highlight: 7]

```kotlin
fun Application.main() {
  install(DefaultHeaders)
  install(CallLogging)
  install(ContentNegotiation) { gson {} }
  routing {
    get("/") { call.respond("OK") }
    get("/events") { call.respond(events) }
  }
}
```

---

# That's it!

TODO Image fly to the moon?

```bash
./gradlew appengineRun
```

---

# Deploy to App Engine

[.code-highlight: none]
[.code-highlight: 1]
[.code-highlight: 3]
[.code-highlight: 5]
[.code-highlight: 7]

```bash
gcloud init

gcloud projects create xebicon-kt-gcp

gcloud config set project xebicon-kt-gcp

gcloud app create

./gradlew appengineDeploy
```

---

# A database?

## Let's try __Datastore__!

---

# Save an event (Java)

[.code-highlight: none]
[.code-highlight: 1]
[.code-highlight: 3]
[.code-highlight: 5]
[.code-highlight: 7-9]
[.code-highlight: 11]

```java
Datastore service = DatastoreOptions.getDefaultInstance().getService();

KeyFactory factory = service.newKeyFactory();

IncompleteKey key = factory.setKind("events").newKey();

FullEntity<IncompleteKey> entity = FullEntity.newBuilder(key)
  .set("title", event.title)
  .build();

service.add(entity);
```

---

# Save an event (Kt)

[.code-highlight: none]
[.code-highlight: 1]
[.code-highlight: 2]
[.code-highlight: 3]
[.code-highlight: 7-9]
[.code-highlight: 11]

```kotlin
post("/events") {
  val event = call.receive<Event>()
  val service = DatastoreOptions.getDefaultInstance().service

  val keyFactory = service.newKeyFactory()
  val key = keyFactory.setKind("events").newKey()
  val dsEvent = FullEntity.newBuilder(key)
    .set("title", event.title)
    .build()

  service.add(event.build(service))
}
```

---

# Kt can do more?

## __Extensions ❤️__

[.code-highlight: none]
[.code-highlight: 1]
[.code-highlight: 3-6]
[.code-highlight: 8-12]

```kotlin
import fr.xebicon.extension.build

fun Event.build(service: Datastore): FullEntity<IncompleteKey> {
  val key = service.newKeyFactory().setKind("events").newKey()
  return FullEntity.newBuilder(key).set("title", title).build()
}

post("/events") {
  val event = call.receive<Event>()
  val service = DatastoreOptions.getDefaultInstance().service
  service.add(event.build(service))
}
```

---

# What we have?

1. Backend in Kt on Appengine (GCP) with Ktor
1. DB (Datastore) enhanced with Kt extensions

---

# Performance and cost

TODO overview on performance over Java? And cost of Appengine?

---

# What next?

## __Does it behave nicely on__
## __AWS Lambda?__

---

- Serverless (easier)

- Or Gradle plugin

---

- Java 8

- Need a fat | uber jar

---

[.code-highlight: none]
[.code-highlight: 3-4]
[.code-highlight: 8-9]
[.code-highlight: 12-13]
[.code-highlight: 16-18]

```gradle
buildscript {
  dependencies {
    classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
    classpath "com.github.jengelman.gradle.plugins:shadow:$shadow_jar_version"
  }
}

apply plugin: 'kotlin'
apply plugin: 'com.github.johnrengelman.shadow'

dependencies {
  compile "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version"
  compile "com.amazonaws:aws-lambda-java-core:$aws_version"
}

task deploy(type: Exec, dependsOn: 'shadowJar') {
  commandLine 'serverless', 'deploy'
}
```

---

# serverless.yml

```yaml
provider:
  name: aws
  runtime: java8
  region: eu-west-3
package:
  artifact: build/libs/kt-aws-save-event-all.jar
functions:
  saveEvent:
    handler: SaveEvent
    memorySize: 1024
    events:
    - http:
        path: save-event
        method: post
```

--- 

# SaveEvent.kt

```kotlin
class SaveEvent : RequestHandler<Map<String, Any>, Unit> {

  override fun handleRequest(input: Map<String, Any>, context: Context) {
    println("Event processed")
  }
}
```

---

## Build & deploy

[.code-highlight: none]
[.code-highlight: 1]
[.code-highlight: 3]
[.code-highlight: 5-7]
[.code-highlight: 9]

```bash
$ ./gradlew shadowJar

$ ./gradlew deploy
```

---

# A database?

## Let's try __Dynamo__

---

# serverless.yml

```yaml
provider:
  name: aws
  runtime: java8
  region: eu-west-3
  iamRoleStatements:
  - Effect: Allow
    Action:
    - dynamodb:PutItem
    Resource: "arn:aws:dynamodb:eu-west-3:*:*"
package:
  artifact: build/libs/kt-aws-save-event-all.jar
functions:
  saveEvent:
    handler: SaveEvent
    memorySize: 1024
    events:
    - http:
        path: save-event
        method: post
```

---

# SaveEvent.kt

```kotlin
class SaveEvent : RequestHandler<Map<String, Any>, Unit> {
  private val mapper = jacksonObjectMapper()
  override fun handleRequest(input: Map<String, Any>, context: Context) {
    val event = mapper.readValue<Event>(input[BODY] as String)
    val client = AmazonDynamoDBClientBuilder.standard().build()
    val db = DynamoDB(client)
    val table = db.getTable(EVENTS)
    table.putItem(event.build())
  }
}
```

---

# Extension :heart:

```kotlin
private fun Event.build(): Item =
  Item().withPrimaryKey("title", title).withString("description", description)
```

---

# What we have done?

- AWS Lambda with Serverless
- DynamoDB + Kotlin extension

---

# What next?

## Can I do Kotlin on __Azure__?

---

# Considerations

- .NET Core SDK
- Core Tools Development
- Azure CLI

- No `Gradle` plugin yet :cry:
- Only `Maven`
- Actually in preview :umbrella: (Java)
- Fat | uber jar as Lambda
- Not supported yet by Serverless (Node)

---

# Structure

```
├── host.json
├── local.settings.json
├── build.gradle
└── src
    └── main
        ├── kotlin
        │   └── Function.kt
        └── resources
            └── saveEvent
                └── function.json
```

---

# Dist

```
├── build
│   ├── azure-functions
│   │   ├── saveEvent
│   │   │   └── function.json
│   │   ├── function.jar
│   │   ├── host.json
│   │   └── local.settings.json
```

---

# Configuration

```json
```

---

# Build

^ Deps fat jar = Lambda
Plugin Kotlin + Shadow
Jar, copie conf + host.json
CLI Azure function

```gradle
dependencies {
  compile "com.microsoft.azure.functions:azure-functions-java-library:$azure_function_version"
}
task run(type: Exec) {
  workingDir $buildDir/azure-functions/
  commandLine 'func', 'host', 'start'
}
task deploy(type: Exec) {
  commandLine 'az', 'functionapp', 'deployment', 'source', 'config-zip', '-g', "${rootProject.name}-group", '-n', "${rootProject.name}", '--src', "${buildDir}/${rootProject.name}.zip"
}
```

---

# SaveEvent.kt

```kotlin
val moshi: Moshi = Moshi.Builder()
  .add(KotlinJsonAdapterFactory())
  .build()

val eventAdapter: JsonAdapter<Event> = moshi.adapter(Event::class.java)

fun saveEvent(data: String, context: ExecutionContext): String {
  val event = eventAdapter.fromJson(data)
  return event?.title ?: data
}
```

---

# A database?

## Let's try __CosmosDB__ 🌎

---

# Function.kt

```kotlin
val client = DocumentClient("https://kt-azure.documents.azure.com:443/",
  KEY,
  ConnectionPolicy.GetDefault(),
  ConsistencyLevel.Session)

val database: Database = client.queryDatabases("SELECT * FROM root r WHERE r.id='KtAzure'", null)
  .queryIterable.toList()[0]

val collection: DocumentCollection = client.queryCollections(database.selfLink, "SELECT * FROM root r WHERE r.id='Events'", null)
  .queryIterable.toList()[0]

fun saveEvent(data: String, context: ExecutionContext): String {
  val eventDocument = Document(data)
  client.createDocument(collection.selfLink, eventDocument, null, false).resource
  return data
}
```

---

# Thats'it!
- Kotlin on Azure Function
- CosmosDb
- Gradle 🔥

---

> Ils ne savaient pas que c’était impossible, alors ils l’ont fait.
-- Mark Twain

---

# Thank you!

## Take away

- cloud.google.com/kotlin/
- github.com/xebia-france/xebicon-kotlin-cloud
- xebicon.fr