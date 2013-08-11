package models
 
import play.api.db._
import anorm._
import play.api.Play.current
import anorm.SqlParser._
 
case class Room (
    room_id:  String,
    chair_id: String,
    tag_id:   String,
    x:        String,
    y:        String,
    status:   String) {
 
    def add {
        DB.withConnection { implicit c =>
            val id: Int = SQL("update room_info set status={status} where room_id={room_id} and chair_id={chair_id} and tag_id={tag_id}").
                on('room_id  -> this.room_id,
                   'chair_id -> this.chair_id,
                   'tag_id   -> this.tag_id,
                   'status   -> this.status
                   ).executeUpdate()
        }
    }
}

object Room {
  val data = {
    get[String]("room_id") ~
    get[String]("chair_id") ~
    get[String]("tag_id") ~
    get[String]("x") ~
    get[String]("y") ~
    get[String]("status") map {
      case room_id ~ chair_id ~ tag_id ~ x ~ y ~ status => Room(room_id, chair_id, tag_id, x, y, status)
    }
  }

  def getAll: List[Room] = {
    DB.withConnection { implicit c =>
      val datas = SQL("SELECT * FROM ROOM_INFO").as(Room.data *)
      return datas
    }
  }
}
