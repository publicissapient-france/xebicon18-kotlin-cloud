import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler

class SaveEvent : RequestHandler<Map<String, Any>, Unit> {

  override fun handleRequest(input: Map<String, Any>?, context: Context?) {
    println("Event saved")
  }

}
