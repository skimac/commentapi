package util

import constants.ResponseTag._
import constants.ResponseStatus._
import play.api.libs.json.{JsArray, JsObject, JsString, JsValue}

/**
 * Server response formatter
 */
object ResponseFormatter {

  def okResponse: JsValue = JsObject {
    Seq {
      STATUS -> JsString(OK)
    }
  }

  def failResponse: JsValue = JsObject {
    Seq {
      STATUS -> JsString(FAIL)
    }
  }

  def badRequestResponse(errorMessage: String): JsValue = JsObject {
    Seq {
      STATUS -> JsString(FAIL)
      ERROR -> JsString(errorMessage)
    }
  }

  def okResponseWithJsObject(jsObject: JsObject): JsObject = JsObject {
    Seq {
      STATUS -> JsString(OK)
      COMMENT -> jsObject
    }
  }

  def okResponseWithJsArray(jsArray: JsArray): JsObject = JsObject {
    Seq {
      STATUS -> JsString(OK)
      COMMENTS -> jsArray
    }
  }
}