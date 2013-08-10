package controllers

import play.api._
import play.api.libs.iteratee.Enumerator
import play.api.libs.iteratee.Iteratee
import play.api.mvc._
import play.api.libs.iteratee.Concurrent

object Application extends Controller {
  
  def index = Action {
    Ok(views.html.map())
  }
  
  val (enumerator, channel) = Concurrent.broadcast[String]
  
  def echowebsocketindex = Action {
    Ok(views.html.websocketindex())
  }
  
  def echowebsocketmessage = WebSocket.using[String] { request => 
    
    val in = Iteratee.foreach[String]{msg =>
      channel.push(msg)
    }.mapDone { _ =>
      println("Disconnected")
    }
    
    val out = enumerator
    
    (in, out)
  }
}