package dal.repository

import dal.config.{Db, DbConfiguration}
import models.Comment
import slick.dbio.DBIOAction
import slick.jdbc.meta.MTable
import dal.tables.CommentsTable

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}


class CommentRepository extends Db with CommentsTable with DbConfiguration {
  import config.profile.api._
  import scala.concurrent.ExecutionContext.Implicits.global


  /**
   * Creates table
   */
  def init: Future[Unit] = db.run(DBIOAction.seq(comments.schema.create))

  /**
   * Creates table if not exists
   */
  Await.result(db.run(DBIOAction.seq(
    MTable.getTables map (tables => {
      if (!tables.exists(_.name.name == comments.baseTableRow.tableName)) {
        init
      }
    })
  )), Duration.Inf)


  def save(comment: Comment): Future[Comment] = db
    .run(comments returning comments.map(com => com.id) += comment)
    .map(id => comment.copy(id = Some(id)))

  def findAll: Future[Seq[Comment]] = db.run(comments.result)

  /**
   * Finds comment containing provided string
   *
   * @param string string to be used for filtering comments
   *
   * @return comments containing provided string
   */
  def findByString(string: String): Future[Seq[Comment]] = {
    val query = for (comment <- comments if comment.content.toUpperCase like s"%${string.toUpperCase()}%")
      yield comment

    db.run(query.result)
  }

  /**
   * Updates comment content
   *
   * @param id comment id
   * @param updatedContent updated content
   *
   * @return true if updated
   */
  def updateContent(id: Long, updatedContent: String): Future[Boolean] = {
    val query = for (comment <- comments if comment.id === id)
      yield comment.content

    db.run(query.update(updatedContent)) map { _ > 0 }
  }

  /**
   * Deletes comment
   *
   * @param id comment id
   *
   * @return true if deleted
   */
  def delete(id: Long): Future[Boolean] = db
    .run(comments.filter(_.id === id).delete)
    .map { _ > 0 }
}