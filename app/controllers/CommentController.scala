package controllers

import constants.ErrorMessage.INVALID_STRUCTURE
import javax.inject.{Inject, Singleton}
import models.Comment
import constants.SortingOrder._
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import util.ResponseFormatter._
import play.api.libs.json.JsArray
import service.CommentService
import util.json.mapper.request.RequestToCommentMapper.mapToModel

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.postfixOps


@Singleton
class CommentController @Inject()(val controllerComponents: ControllerComponents, val commentService: CommentService) extends BaseController {

  /**
   * Creates new comment
   *
   * @return JSON object with saved comment
   */
  def createComment(): Action[AnyContent] = Action.async { request =>
    val optionalComment = mapToModel(request)

    optionalComment match {
      case Some(comment) => commentService.save(comment)
          .map(savedComment => {
            val responseObject = Comment.asJson(savedComment)
            Created(okResponseWithJsObject(responseObject))
          })

      case None => Future { BadRequest(badRequestResponse(INVALID_STRUCTURE)) }
    }
  }


  /**
   * Lists comments
   *
   * @param orderOption optional parameter for ordering results
   * @param filterOption optional parameter for filtering results
   * @param prefixOption optional parameter for prefixing results
   *
   * @return JSON object with results
   */
  def getAllComments(orderOption: Option[Order], filterOption: Option[String], prefixOption: Option[String]): Action[AnyContent] = Action.async {
    filterOption match {
      case Some(filter) =>
        commentService.findByString(filter)
          .map(comments => {
            val resultComments = commentService.sortAndAddPrefix(comments, orderOption, prefixOption)
            val commentsAsJsons = resultComments.map(prefixedComment => Comment.asJson(prefixedComment))

            Ok(okResponseWithJsArray(JsArray(commentsAsJsons)))
          })

      case None =>
        commentService.findAll
          .map(comments => {
            val resultComments = commentService.sortAndAddPrefix(comments, orderOption, prefixOption)
            val commentsAsJsons = resultComments.map(prefixedComment => Comment.asJson(prefixedComment))

            Ok(okResponseWithJsArray(JsArray(commentsAsJsons)))
          })
    }
  }

  /**
   * Updates comment
   *
   * @param id comment id
   *
   * @return Ok or Fail response
   */
  def updateComment(id: Long): Action[AnyContent] = Action.async { request =>
    val commentOption = mapToModel(request)

    commentOption match {
      case Some(comment) => commentService.update(id, comment.content)
        .map {
          case true => Ok(okResponse)
          case false => Ok(failResponse)
        }

      case None => Future { BadRequest(badRequestResponse(INVALID_STRUCTURE)) }
    }
  }

  /**
   * Deletes comment
   *
   * @param id comment id
   *
   * @return Ok or Fail response
   */
  def deleteComment(id: Long): Action[AnyContent] = Action.async {
    commentService.delete(id)
        .map {
          case true => Ok(okResponse)
          case false => Ok(failResponse)
        }
  }
}