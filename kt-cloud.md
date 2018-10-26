theme: Fira, 6
slidenumbers: true

![](https://xebicon.fr/wp-content/uploads/2018/06/Xebicon18-brongniart-tech4exec.jpg)

## XebiCon'__18__

# Kotlin in your __Cloud__

---

# Who we are?

![left fit filtered](blacroix.jpg)

Benjamin Lacroix

@benjlacroix

Fullstack Developer
& Manager

---

# Who we are?

![left fit filtered](blacroix.jpg)

Paul-Guillaume

Fullstack Developer
& Manager

---

# Agenda

- Kotlin?
- Google Cloud Platform
- Amazon Web Services
- Azure

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
- GCloud App Engine Java: `gcloud components install app-engine-java`
- Use GCloud Github repository for Ktor and AppEngine

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

val amaze = Speaker("Amaze")

val events = listOf(
  Event("Keynote",
    "Amazing Keynote from an amazing speaker",
    listOf(amaze),
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

TODO fly to the moon

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

## Let's try Cloud Datastore!

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

## Kt extensions ❤️

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

# What next?

## Does it behave nicely on serverless AWS Lambda?

---
