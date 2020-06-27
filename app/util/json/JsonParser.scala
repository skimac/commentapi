package util.json

import play.api.libs.json.JsValue

/**
 * Parses instance of T class to JSON
 *
 * @tparam T class to be parsed
 */
trait JsonParser[T] {

  /**
   * @param model model to be parsed
   *
   * @return model as JSON
   */
  def asJson(model: T): JsValue
}