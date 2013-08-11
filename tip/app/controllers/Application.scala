package controllers

import play.api._
import play.api.libs.iteratee.Enumerator
import play.api.libs.iteratee.Iteratee
import play.api.mvc._
import play.api.libs.iteratee.Concurrent

object Application extends Controller {
  val (enumerator, channel) = Concurrent.broadcast[String]
  
  def index = Action {
    Ok(views.html.map())
  }
  
  def ws = WebSocket.using[String] { request => 
    val in = Iteratee.foreach[String]{msg =>
    }.mapDone { _ =>
      println("Disconnected")
    }
    
    val out = enumerator
    
    (in, out)
  }
  
  def push = Action {
    channel.push("{\"roomid\":100,\"roomlat\":35,\"roomlng\":139,\"roomstat\":0,\"totalnum\":2,\"emptynum\":1,\"seats\":[{\"seatid\":10,\"seatstatus\":0},{\"seatid\":11,\"seatstatus\":1}]}")
    Ok("pushed")
  }
}