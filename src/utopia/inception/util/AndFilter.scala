package utopia.inception.util

/**
 * This event filter only accepts event accepted by all of the included filters
 * @author Mikko Hilpinen
 * @since 21.1.2017
 */
class AndFilter[T](val filters: Filter[T]*) extends Filter[T]({ event => filters.forall { _(event) } })