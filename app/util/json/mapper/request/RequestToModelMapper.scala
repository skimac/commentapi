package util.json.mapper.request

import play.api.mvc.{AnyContent, Request}

/**
 * Maps request to model
 *
 * @tparam T type of model
 */
trait RequestToModelMapper[T] {

  /**
   * @param request request object
   *
   * @return optional model
   */
  def mapToModel(request: Request[AnyContent]): Option[T]
}