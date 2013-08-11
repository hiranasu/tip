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
        
        // get emptynum and chair info by room
        var jsonRes = "{\"rooms\":["
        roomInfo.foreach {e => {
          jsonRes += "{\"roomid\":\"" + e.room_id + "\",\"totalnum\":" + e.totalnum + ",\"roomlat\":" + e.x + ",\"roomlon\":" + e.y + ","
          
          var emptyNum = 0
          
          val emptyNumByRoom: List[EmptyNum]  = Room.getEmptyNumByRoom(e.room_id)
          emptyNumByRoom.foreach{e =>
            emptyNum = e.empty_num.toInt
            jsonRes += "\"emptynum\":" + e.empty_num + ","
          }

          var roomStat = 0
          if(emptyNum / e.totalnum > 0.5) {
            roomStat = 0
          } else if (emptyNum / e.totalnum > 0) {
            roomStat = 1
          } else {
            roomStat = 2
          }
          println("room=" + e.room_id + " roomstat=" + roomStat)
            
          jsonRes += "\"roomstat\":" + roomStat + ",\"seats\":["
          val chairStatByRoom:List[ChairStat] = Room.getChairStatByRoom(e.room_id)
          chairStatByRoom.foreach{e=>
            jsonRes += "{\"seatid\":\"" + e.chair_id + "\",\"seatstatus\":" + e.status +"},"
          }
          jsonRes = jsonRes.substring(0, jsonRes.length() - 1)
          jsonRes += "]"
          }
          jsonRes += "},"
        }
        jsonRes = jsonRes.substring(0, jsonRes.length() - 1)
        jsonRes += "]}"
        
        // push websocket message
        Application.channel.push(jsonRes)
        Ok("end")
  }
  
}
