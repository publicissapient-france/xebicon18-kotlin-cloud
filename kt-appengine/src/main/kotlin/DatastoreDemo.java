import com.google.cloud.datastore.*;

class Event {
  String title;

  Event(String title) {
    this.title = title;
  }
}

public class DatastoreDemo {

  private void addEntity() {
    Event event = new Event("Kt in cloud");
    Datastore service = DatastoreOptions.getDefaultInstance().getService();
    KeyFactory factory = service.newKeyFactory();
    IncompleteKey key = factory.setKind("events").newKey();
    FullEntity<IncompleteKey> entity = FullEntity.newBuilder(key)
      .set("title", event.title)
      .build();
    service.add(entity);
  }
}
