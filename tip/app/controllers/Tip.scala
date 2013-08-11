package controllers

import play.api._
import play.api.mvc._
import models._

object Tip extends Controller {
  
  def update = Action {
    implicit request =>
        //val id = request.queryString.getOrElse("tag", "")
        val id = request.queryString.get("tag").flatMap(_.headOption).mkString
        val params = id split '_' 
        val status = request.queryString.get("status").flatMap(_.headOption).mkString
        val room: Room  = Room(params(0), params(1), params(2), null, null, status) 
        val result = room.add
        Ok("end")
  }
  
}
