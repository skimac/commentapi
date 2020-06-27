package util.json.mapper

import play.api.libs.json.JsValue

/**
 * Maps JSON to an instance of T class
 *
 * @tparam T class to which JSON has to be mapped
 */
trait JsonMapper[T] {

  /**
   * @param json json to be mapped
   *
   * @return optional T instance
   */
  def fromJson(json: JsValue): Option[T]
}
