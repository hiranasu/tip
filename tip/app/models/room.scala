package models
 
import play.api.db._
import anorm._
import play.api.Play.current
import anorm.SqlParser._
 
case class Room (
    room_id: String,
    tag_id: String,
    status: String) {
 
    def add {
        DB.withConnection { implicit c =>
            val id: Int = SQL("update room_info set status={status} where room_id={room_id} and tag_id={tag_id}").
                on('room_id-> this.room_id,
                   'tag_id -> this.tag_id,
                   'status -> this.status
                   ).executeUpdate()
        }
    }
}
