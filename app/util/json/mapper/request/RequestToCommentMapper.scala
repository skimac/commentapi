package util.json.mapper.request

import models.Comment
import play.api.mvc.{AnyContent, Request}

object RequestToCommentMapper extends RequestToModelMapper[Comment] {

  override def mapToModel(request: Request[AnyContent]): Option[Comment] = {
    val optionalJson = request.body.asJson

    optionalJson match {
      case Some(json) => Comment.fromJson(json)
      case None => None
    }
  }
}