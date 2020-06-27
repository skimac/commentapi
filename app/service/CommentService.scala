package service

import constants.SortingOrder.{NATURAL, Order, REVERSE}
import dal.repository.CommentRepository
import javax.inject.Inject
import models.Comment

import scala.concurrent.Future

class CommentService @Inject()(val commentRepository: CommentRepository) {

  def save(comment: Comment): Future[Comment] = commentRepository.save(comment)

  def update(id: Long, content: String): Future[Boolean] = commentRepository.updateContent(id, content)

  def delete(id: Long): Future[Boolean] = commentRepository.delete(id)

  def findAll: Future[Seq[Comment]] = commentRepository.findAll

  def findByString(string: String): Future[Seq[Comment]] = commentRepository.findByString(string)

  /**
   * Prefixes and sorts comments if needed
   *
   * @return prefixed and sorted comments
   */
  def sortAndAddPrefix(comments: Seq[Comment], orderOption: Option[Order], prefixOption: Option[String]): Seq[Comment] = {
    val sortedComments = orderOption match {
      case Some(sortOrder) => sortByContent(comments, sortOrder)
      case None => comments
    }

    addPrefix(sortedComments, prefixOption)
  }

  /**
   * Sorts comments by content with the specified order
   *
   * @param comments comments to be sorted
   * @param sortOrder sorting order
   *
   * @return sorted comments
   */
  private def sortByContent(comments: Seq[Comment], sortOrder: Order): Seq[Comment] = {
    sortOrder match {
      case NATURAL => comments.sortBy(comment => comment.content)
      case REVERSE => comments.sortBy(comment => comment.content).reverse
    }
  }

  /**
   * Adds prefix to comments
   *
   * @param comments comments to be prefixed
   * @param prefixOption optional prefix
   *
   * @return prefixed comments
   */
  private def addPrefix(comments: Seq[Comment], prefixOption: Option[String]): Seq[Comment] = {
    comments
      .map(comment => comment.copy(content = prefixOption.getOrElse("") + comment.content))
  }
}