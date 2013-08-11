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
    get[String]("x") ~
    get[String]("y") ~
    get[String]("status") map {
      case room_id ~ chair_id ~ x ~ y ~ status => Room(room_id, chair_id, null, x, y, status)
    }
  }

  def getAll: List[Room] = {
    DB.withConnection { implicit c =>
      val datas = SQL("SELECT room_id ,chair_id, (CASE WHEN SUM(CONVERT(status,INTEGER)) >= 2 THEN '1' ELSE '0' END) AS status, x, y FROM ROOM_INFO GROUP BY room_id, chair_id").as(Room.data *)
      return datas
    }
  }

  // all room info
  val roomInfo = {
    get[String]("room_id") ~
    get[Long]("totalnum") ~
    get[String]("x") ~
    get[String]("y") map {
      case room_id ~ totalnum ~ x ~ y => RoomInfo(room_id, totalnum, x, y)
    }
  }
  
  def getRoomInfo: List[RoomInfo] = {
    DB.withConnection { implicit c =>
      val datas = SQL("SELECT room_id, count(*) totalnum, x, y FROM (SELECT room_id, chair_id, x, y FROM ROOM_INFO GROUP BY room_id, chair_id) GROUP BY room_id").as(Room.roomInfo *)
      return datas
    }
  }  
  
  // empty num by room
  val emptyNumByRoom = {
    get[Long]("empty_num") map {
      case empty_num => EmptyNum(empty_num)
    }
  }

  def getEmptyNumByRoom(room_id:String) : List[EmptyNum] = {
    DB.withConnection { implicit c =>
      val datas = SQL("SELECT count(*) EMPTY_NUM FROM (SELECT room_id, chair_id, (CASE WHEN SUM(CONVERT(status,INTEGER)) >= 2 THEN '1' ELSE '0' END) AS status FROM ROOM_INFO  WHERE room_id = {room_id} GROUP BY room_id, chair_id) WHERE STATUS = 0").on('room_id -> room_id).as(Room.emptyNumByRoom *)
      return datas
    }    
  }

  // chair info by room
  val chairStatByRoom = {
    get[String]("chair_id") ~
    get[String]("status")  map {
      case chair_id ~ status => ChairStat(chair_id, status)
    }
  }
  
  def getChairStatByRoom(room_id:String) : List[ChairStat] = {
    DB.withConnection { implicit c =>
      val datas = SQL("SELECT chair_id, (CASE WHEN SUM(CONVERT(status,INTEGER)) >= 2 THEN '1' ELSE '0' END) AS status FROM ROOM_INFO  WHERE room_id = {room_id} GROUP BY room_id, chair_id").on('room_id -> room_id).as(Room.chairStatByRoom *)
      return datas
    }    
  }
}


case class RoomInfo (
    room_id:   String,
    totalnum: Long,
    x:  String,
    y:  String){}

case class EmptyNum (
    empty_num: Long){}

case class ChairStat (
    chair_id:  String,
    status: String){}
