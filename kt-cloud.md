theme: XebiCon18
slidenumbers: true

![](https://xebicon.fr/wp-content/uploads/2018/06/Xebicon18-brongniart-tech4exec.jpg)

## XebiCon'__18__

# Kotlin in your __Cloud__

^ B

---

# Who we are?

![left filtered](blacroix.jpg)

Benjamin Lacroix

**@benjlacroix**

Android developer

^ B

---

# Who we are?

![left filtered](pg.jpg)

Paul-Guillaume Déjardin

**@pgdejardin**

Backend developer

^ PG

---

# Agenda
![10%](https://upload.wikimedia.org/wikipedia/commons/thumb/7/74/Kotlin-logo.svg/1200px-Kotlin-logo.svg.png)

![45%](https://www.silicon.it/wp-content/uploads/2017/06/Google-Cloud-Platform.png)
![13%](https://fbrazeal.files.wordpress.com/2016/06/lambda.png?w=1200)
![25%](https://adatumno.azureedge.net/wp-content/uploads/2018/07/functions-logo.png?2aa027)

- Kotlin?
- Appengine on **GCP**
- Lambda on **AWS**
- Function on **Azure**

^ B

---

# Whose for?

- **Android** developer 
wanting to build backend apps

- **Backend** developer
thinking of switching to Kotlin

^ Partager du code
Utiliser des bibliothèques connues

^ B

---

# Why?

![](https://blog.socialcops.com/wp-content/uploads/2017/05/FeaturedImage-Android-Kotlin-Development-Engineering-SocialCops-Blog.png)

- It enforces no particular philosophy of programming

- Kotlin is already on Android

- Kotlin is more elegant than Java and easier than Scala ✨

- Safe, concise and interoperable

^ B

---

![left 30%](https://upload.wikimedia.org/wikipedia/commons/thumb/1/1a/JetBrains_Logo_2016.svg/1200px-JetBrains_Logo_2016.svg.png)

# What?

- Statically typed programming language that targets JVM, Android, Javascript and Native

- Developed by JetBrains

- Established in 2011 and open-sourced early on

- version 1.0 released in 2016

^ PG

---

# Null Safety

[.code-highlight: none]
[.code-highlight: 1]
[.code-highlight: 2]
[.code-highlight: 3]
[.code-highlight: 7]
[.code-highlight: 8]
[.code-highlight: none]

```kotlin
val property: User = User("Paul", "Smith") // OK
val secondProperty: User = null // Not OK
val thirdProperty: User? = null // OK

// Unwrapping null values

thirdProperty.name // Not OK
thirdProperty?.name // OK
```

# Elvis Operator

[.code-highlight: none]
[.code-highlight: 1]

```kotlin
thirdProperty?.something ?: "else value"
```

^ PG

---

# Immutability

[.code-highlight: none]
[.code-highlight: 1]
[.code-highlight: 1-2]
[.code-highlight: 4]
[.code-highlight: 4-5]

```kotlin
var variable: String = "I am mutable..."
variable = "Am I really mutable?" // OK

val immutableValue: String = "I am immutable!!!"
immutableValue = "Could I have another value?" // NOT OK!!! 
```

^ PG

---

# Concise

^ PG

---

# User.java 1/2

[.code-highlight: none]
[.code-highlight: 1, 10]
[.code-highlight: 1-5, 10]
[.code-highlight: 1, 7-8, 10]

```java
public class User {
  private String firstname;
  private String lastname;
  private Date birthdate;
  // ...
  
  public String getFirstname() { return this.firstname; }
  public String setFirstname(String firstname) { this.firstname = firstname; }
  // ...
}
```

^ PG

---

# User.java 2/2

[.code-highlight: 1-9, 22]
[.code-highlight: 1, 11-12, 22]
[.code-highlight: 1, 14-21, 22]

```java
public class User {  
  @Override
  public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      User user = (User) o;
      return Objects.equals(firstname, user.firstname) && 
      Objects.equals(lastname, user.lastname) && Objects.equals(birthdate, user.birthdate);
  }
  
  @Override
  public int hashCode() { return Objects.hash(firstname, lastname, birthdate); }

  @Override
  public String toString() {
      return "User{" +
          "firstname='" + firstname + '\'' +
          ", lastname='" + lastname + '\'' +
          ", birthdate=" + birthdate +
          '}';
  }
}
```

^ PG

---

# User.kt

```kotlin
data class User(
  val firstname: String,
  val lastname: String,
  val birthdate: Date
)
```

That's all... 💪

^ PG

---

# Extensions

[.code-highlight: none]
[.code-highlight: 1]
[.code-highlight: 3]
[.code-highlight: 5]

```kotlin
package fr.xebicon.extension

fun Int.square(): Int = this * this

2.square() // 4

```

^ PG

---

# Many other things

- coroutines
- default value in parameters
- easier syntax
- etc.

^ PG
Passation: JetBrains est le créateur, mais comme vu plus tôt, Kotlin est sur Android et Google le pousse énormément.
Le travail de ces 2 boites a donné naissance à la... Kotlin Foundation 

---

### **Mission is to protect, promote and advance the development of the Kotlin programming language.**

#### - _Kotlin foundation ( Google + Jetbrains )_

^ B
4th October + Kotlin portal sur GCP
The Foundation secures Kotlin’s development and distribution as Free Software, meaning that it is able to be freely copied, modified and redistributed, including modifications to the official versions.

---

# Google Cloud Platform

![left fit](https://www.silicon.it/wp-content/uploads/2017/06/Google-Cloud-Platform.png)

- Java 8

- Gradle

- Kotlin

- Ktor

^ B

---

##  __Easy to use, fun and asynchronous__
####  *- ktor.io*

^ B

---

# Requirements

- Google Cloud CLI
- GCloud App Engine Java
- GCloud Github repository for 
Ktor and AppEngine

^ B

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
  implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version"
  implementation "io.ktor:ktor-server-servlet:$ktor_version"
  implementation "io.ktor:ktor-gson:$ktor_version"
  providedCompile "com.google.appengine:appengine:$appengine_version"
}
```

^ B

---

# application.conf

```
ktor {
  application {
    modules = [fr.xebicon.AppKt.main]
  }
}
```

^ B

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

^ B

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

^ B

---

# Deploy to App Engine

![](https://www.mediaan.com/wp-content/uploads/2017/01/launch17.jpg)

[.code-highlight: none]
[.code-highlight: 1]
[.code-highlight: 3]
[.code-highlight: 5]
[.code-highlight: 7]
[.code-highlight: 9]

```bash
gcloud init

gcloud projects create xebicon-kt-gcp

gcloud config set project xebicon-kt-gcp

gcloud app create

./gradlew appengineDeploy
```

^./gradlew appengineRun (local)

^ B

---

![left 30%](http://google.tieto.com/images/services/google-cloud-platform/CloudStorage_500px-865820cf.png)

# A database?

## Let's try __Datastore__!

^ BDD NoSQL
ACID, SQL, index
B

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

^ B

---

# Save an event (Kt)

[.code-highlight: none]
[.code-highlight: 1]
[.code-highlight: 2]
[.code-highlight: 3]
[.code-highlight: 5-6]
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

  service.add(dsEvent)
}
```

^ B

---

# Kt can do more?
## __Extensions ❤️__

[.code-highlight: none]
[.code-highlight: 1]
[.code-highlight: 3-4]
[.code-highlight: 6-12]

```kotlin
package fr.xebicon.extension

private val service: Datastore = 
  DatastoreOptions.getDefaultInstance().service

fun Event.save(): Entity? {
  val key = service.newKeyFactory().setKind("events").newKey()
  val entity = FullEntity.newBuilder(key)
    .set("title", title)
    .build()
  return service.add(entity)
}
```

^ B

---

Kt can do more?
## __Extensions ❤️__

[.code-highlight: none]
[.code-highlight: 1]
[.code-highlight: 5]
[.code-highlight: 3-7]

```kotlin
import fr.xebicon.extension.save
// ...
post("/events") {
  val event = call.receive<Event>()
  event.save()
  call.respond("OK")
}
```

^ B

---

![inline 50%](https://camo.githubusercontent.com/ff8d543d1bc5951292d40f105ca2a96d6eeee1fa/687474703a2f2f6b746f722e696f2f6173736574732f696d616765732f6b746f725f6c6f676f2e706e67)

# What we have?

1. Backend in Kt on Appengine (GCP) with Ktor
2. DB (Datastore) enhanced with Kt extensions

^ B

---

# What next?

## __Does it behave nicely on__
## __AWS Lambda?__

^ PG

---

![inline 30%](https://lever-client-logos.s3.amazonaws.com/3a11c9ce-98fc-4715-9bdb-c4c4d924ef7d-1508186423731.png)

- Serverless (easier)

- Or Gradle plugin

^ PG
NPM pour l'install

---

- Java 8

- Need a fat | uber jar

^ PG

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
  implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version"
  implementation "com.amazonaws:aws-lambda-java-core:$aws_version"
}

task deploy(type: Exec, dependsOn: 'shadowJar') {
  commandLine 'serverless', 'deploy'
}
```

^ PG
DEPS DE BUILD
PLUGINS
DEPS DE RUNTIME
AJOUT D'UNE TACHE

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

^ PG

--- 

# SaveEvent.kt

```kotlin
class SaveEvent : RequestHandler<Map<String, Any>, Unit> {

  override fun handleRequest(input: Map<String, Any>, context: Context) {
    println("Event processed")
  }
}
```

^ PG

---

## Build & deploy

[.code-highlight: none]
[.code-highlight: 1]
[.code-highlight: 3]

```bash
./gradlew shadowJar

./gradlew deploy
```

^ PG

---

# A database?

## Let's try __Dynamo__

![left 40%](https://cdn-images-1.medium.com/max/1200/1*qp3u7D_FkGlFeBPUx7hcLg.png)

^ PG

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

^ PG
AJOUT DU ROLE IAM POUR INSERER DANS DYNAMO

---

# SaveEvent.kt

[.code-highlight: 1]
[.code-highlight: 3, 9]
[.code-highlight: 3, 9, 4]
[.code-highlight: 3, 9, 5-8]

```kotlin
import fr.xebicon.extension.save

class SaveEvent : RequestHandler<Map<String, Any>, Unit> {
  private val mapper = jacksonObjectMapper()
  override fun handleRequest(input: Map<String, Any>, context: Context) {
    val event = mapper.readValue<Event>(input[BODY] as String)
    event.save()
  }
}
```

^ PG

---

# Extension ❤️

[.code-highlight: 1]
[.code-highlight: 3]
[.code-highlight: 4]
[.code-highlight: 5]
[.code-highlight: 7-10]

```kotlin
package fr.xebicon.extension

val client: AmazonDynamoDB = AmazonDynamoDBClientBuilder.standard().build()
val db = DynamoDB(client)
val table: Table = db.getTable(EVENTS)

fun Event.save(): PutItemOutcome? =
  table.putItem(Item()
    .withPrimaryKey("title", title)
    .withString("description", description))
```

^ PG

---

![30%](https://fbrazeal.files.wordpress.com/2016/06/lambda.png?w=1200)
![30%](https://cdn-images-1.medium.com/max/1200/1*qp3u7D_FkGlFeBPUx7hcLg.png)

# What we have done?

- AWS Lambda with Serverless
- DynamoDB + Kotlin extension

^ PG

---

![right 50%](https://twhyderabad.github.io/xtremetesting/static/media/performance-testing.5b7a5cb2.png)

# Serverless & JVM? Really?

- Cold Start (~1s)

- Approximately same perf as Go

- Consistent performance of compiled vs dynamic (JVM, .NET, Go Vs JS & Python)

- Works well without framework

^ PG

---

![left 60%](https://adatumno.azureedge.net/wp-content/uploads/2018/07/functions-logo.png?2aa027)

# What next?

## Can I do Kotlin on __Azure__?

^ B

---

# Considerations

- .NET Core SDK
- Core Tools Development
- Azure CLI

- No `Gradle` plugin yet :cry:
- Only `Maven`
- Currently in preview :umbrella: (Java)
- Fat | Uber jar as Lambda
- Not supported yet by Serverless (Node)

^
Pré-requis
CLI MacOS (Brew) 
B

---

# Structure

[.code-highlight: none]
[.code-highlight: 1]
[.code-highlight: 2]
[.code-highlight: 3]
[.code-highlight: 4-7]
[.code-highlight: 4-5, 8-10]

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

^host.json : config globale toutes functions, v2, liste les functions projet
local.settings.json : config de l'app (runtime, nom du projet)

^ B

---

# Dist

[.code-highlight: none]
[.code-highlight: 3-4]
[.code-highlight: 5]
[.code-highlight: 6]

```
└── build
    └── azure-functions
        ├── saveEvent
        │   └── function.json
        ├── function.jar
        └── host.json
```

^jar créé avec shadowJar
⚠️ respecter l'arborescence des functions
Faire un zip puis utiliser az pour déployer
Pas facile, nécessite de créer la function avant

^ B

---

# function.json

[.code-highlight: none]
[.code-highlight: 2]
[.code-highlight: 3]
[.code-highlight: 4-12]

```json
{
  "scriptFile": "../function.jar",
  "entryPoint": "fr.xebicon.FunctionKt.saveEvent",
  "bindings": [
    {
      "type": "httpTrigger",
      "name": "data",
      "direction": "in",
      "dataType": "string",
      "authLevel": "anonymous",
      "methods": [ "post" ]
    }
  ]
}
```

^function.jar : créé avec shadowJar
FunctionKt car fichier Function.kt
Function saveEvent dans le fichier (sans class)
Config
bindings>name : nom du paramètre de la fonction

^ B

---

# Build

[.code-highlight: none]
[.code-highlight: 1-3]
[.code-highlight: 5-8]
[.code-highlight: 10-14]

```gradle
dependencies {
  implementation "...:azure-functions-java-library:$azure_function_version"
}

task run(type: Exec) {
  workingDir $buildDir/azure-functions/
  commandLine 'func', 'host', 'start'
}

task deploy(type: Exec) {
  commandLine 'az', 'functionapp', 'deployment', 'source', 'config-zip', 
  '-g', "${rootProject.name}-group", '-n', "${rootProject.name}", 
  '--src', "${buildDir}/${rootProject.name}.zip"
}
```

^ Deps fat jar = Lambda
Plugin Kotlin + Shadow
Jar, copie conf + host.json
CLI Azure function
B

---

# SaveEvent.kt

[.code-highlight: none]
[.code-highlight: 1]
[.code-highlight: 2]
[.code-highlight: 1-4]

```kotlin
fun saveEvent(data: String, context: ExecutionContext): String {
  context.logger.info("saveEvent called with $data")
  return "OK"
}
```

---

![left 70%](https://azure.microsoft.com/svghandler/cosmos-db?width=1200&height=630)

# A database?

## Let's try __CosmosDB__ 🌎

^DB bdd multi-modèle distribuée, SQL, MongoDB, Cassandra, etc.

^ B

---

# SaveEvent.kt

[.code-highlight: none]
[.code-highlight: 1-2]
[.code-highlight: 4]
[.code-highlight: 5]
[.code-highlight: 6]
[.code-highlight: 4-8]

```kotlin
import fr.xebicon.extension.save
import fr.xebicon.extension.toEvent

fun saveEvent(data: String): String {
  val event = data.toEvent()
  event?.save()
  return event?.title ?: "unknown"
}
```

---
 
# StringExtension.kt ❤️

[.code-highlight: none]
[.code-highlight: 1-3]
[.code-highlight: 5-6]
[.code-highlight: 8]

```kotlin
val moshi: Moshi = Moshi.Builder()
  .add(KotlinJsonAdapterFactory())
  .build()

val eventAdapter: JsonAdapter<Event> = 
  moshi.adapter(Event::class.java)

fun String.toEvent(): Event? = eventAdapter.fromJson(this)
```

^Code Moshi (parsing JSON)
B

---
# EventExtension.kt ❤️

[.code-highlight: none]
[.code-highlight: 1-3]
[.code-highlight: 5-7]
[.code-highlight: 9-11]
[.code-highlight: 13-16]

```kotlin
val client = DocumentClient("...", KEY,
  ConnectionPolicy.GetDefault(),
  ConsistencyLevel.Session)

val database: Database = 
  client.queryDatabases("SELECT * FROM root r WHERE r.id='KtAzure'", null)
  .queryIterable.toList()[0]

val collection: DocumentCollection = 
  client.queryCollections(database.selfLink, "SELECT * FROM root r WHERE r.id='Events'", null)
  .queryIterable.toList()[0]

fun Event.save(): Document {
  val eventDocument = Document(eventAdapter.toJson(this))
  return client.createDocument(collection.selfLink, eventDocument, null, false).resource
}
```

^Connexion à la DB (clé secrete)
Query DB
Query Collection
Créer un document sur un collection (JSON directement)
B

---
![40%](https://adatumno.azureedge.net/wp-content/uploads/2018/07/functions-logo.png?2aa027)
![50%](https://azure.microsoft.com/svghandler/cosmos-db?width=1200&height=630)
![30%](./gradle.png)
# That's it!
- Kotlin on Azure Function
- CosmosDb
- Gradle 🔥

^Ça a bien fonctionné sauf 
- Création de la function sur Azure
- Création de la table et de la collection sur Azure

^ B

---

##  __They did not know it was impossible so they did it.__
####  *- Mark Twain*

^ PG

---

# What we have done?

✅ Kotlin + AppEngine 👍
✅ Kotlin + Serverless 👍
✅ Kotlin + Azure function 👎
Less vendor lock-in 👍
Kotlin is a great alternative for Java 👍

---

# Also...

![60%](https://puppet.com/sites/default/files/2016-03/docker-logo-lg.png)
![60%](https://i1.wp.com/www.martinmajewski.net/wordpress/wp-content/uploads/2018/03/KotlinNativeOnMacLogo.png?fit=600%2C600&ssl=1)

- Kotlin works with **Docker** using:
Ktor
Spring boot
Javalin...
- Kotlin can compile to **native**:
Run on iOS 📱

^ PG

---

# Take away

- cloud.google.com/kotlin/
- github.com/xebia-france/xebicon-kotlin-cloud
- xebicon.fr
- le-mois-du-kotlin.xebia.fr
(Kotlin ❤️ JavaScript)

^ PG

---

![](https://xebicon.fr/wp-content/uploads/2018/06/Xebicon18-brongniart-tech4exec.jpg)

# [fit] __Thank you!__

## XebiCon'**18**

^ PG
