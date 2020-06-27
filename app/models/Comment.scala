package models

import play.api.libs.json.{JsError, JsObject, JsSuccess, JsValue, Json, OFormat}
import util.json.JsonParser
import util.json.mapper.JsonMapper



case class Comment(id: Option[Long] = None, content: String)

object Comment extends JsonMapper[Comment] with JsonParser[Comment] {
  implicit val commentFormat: OFormat[Comment] = Json.format[Comment]


  def asJson(comment: Comment): JsObject = Json.toJsObject(comment)

  def fromJson(json: JsValue): Option[Comment] = {
    val parsingResult = Json.fromJson[Comment](json)

    parsingResult match {
      case JsSuccess(comment, _) => Some(comment)
      case JsError(_) => None
    }
  }
}