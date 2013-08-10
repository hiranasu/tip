package controllers

import play.api._
import play.api.libs.iteratee.Enumerator
import play.api.libs.iteratee.Iteratee
import play.api.mvc._

object Application extends Controller {
  
	def index = WebSocket.using[String] { request => 
	  
	  // Log events to the console
	  val in = Iteratee.foreach[String](println).mapDone { _ =>
	    println("Disconnected")
	  }
	  
	  // Send a single 'Hello!' message
	  val out = Enumerator("Hello!").andThen(Enumerator.eof)
	  
	  (in, out)
	}
	
	def echowebsocketindex = Action {
		Ok(views.html.websocketindex())
	}
	
	def echowebsocketmessage = WebSocket.using[String] { request => 
	  
	  // Log events to the console
	  val in = Iteratee.foreach[String](println).mapDone { _ =>
	    println("Disconnected")
	  }
	  
	  // Send a single 'Hello!' message
	  val out = Enumerator("Hello!").andThen(Enumerator.eof)
	  
	  (in, out)
	}
}