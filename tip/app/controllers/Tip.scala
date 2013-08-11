package controllers

import play.api._
import play.api.mvc._
import models._
import play.api.libs.iteratee.Concurrent
import play.libs.Json

object Tip extends Controller {
  
  def update = Action {
    implicit request =>
        // insert DB
        val id = request.queryString.get("tag").flatMap(_.headOption).mkString
        val params = id split '_' 
        val status = request.queryString.get("status").flatMap(_.headOption).mkString
        val room: Room  = Room(params(0), params(1), params(2), null, null, status) 
        val result = room.add
        
        // select DB
        // getAll room
        val roomInfo:List[RoomInfo] = Room.getRoomInfo
        roomInfo.foreach(println)
        
        // get emptynum and chair info by room
        roomInfo.foreach {e => {
            val emptyNumByRoom: List[EmptyNum]  = Room.getEmptyNumByRoom(e.room_id)
            emptyNumByRoom.foreach(println)

            val chairStatByRoom:List[ChairStat] = Room.getChairStatByRoom(e.room_id)
            chairStatByRoom.foreach(println)
          }
        }
        
        // push websocket message
        val msg = Json.toJson(4).toString //TODO roomInfo to json data
        println("msg" + msg)
        
        Application.channel.push(msg)
        Ok("end")
  }
  
}
