package constants

import play.api.mvc.QueryStringBindable

/**
 * Sorting order type
 */
object SortingOrder extends Enumeration {
  type Order = Value

  val NATURAL: SortingOrder.Value = Value("natural")
  val REVERSE: SortingOrder.Value = Value("reverse")


  /**
   * Query string binder
   */
  implicit object searchTypeQueryStringBinder
    extends QueryStringBindable.Parsing[Order](
      withName, _.toString,
      (key: String, exception: Exception) => "Cannot parse %s as sort type: %s".format(key, exception.getMessage))
}