package dal.tables


import dal.config.Db
import models.Comment
import slick.lifted.ProvenShape

trait CommentsTable { this: Db => import config.profile.api._

  class Comments(tag: Tag) extends Table[Comment](tag, "comments") {
    def id: Rep[Long] = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def content: Rep[String] = column[String]("content", O.Length(512))

    override def * : ProvenShape[Comment] = (id.?, content) <> ((Comment.apply _).tupled, Comment.unapply)
  }

  val comments = TableQuery[Comments]
}