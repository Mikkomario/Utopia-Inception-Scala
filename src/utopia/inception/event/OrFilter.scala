package utopia.inception.event

/**
 * This filter uses multiple filters creating a logical OR statement. The filter accepts an 
 * event if any of the included filters accepts it
 * @author Mikko Hilpinen
 * @since 17.10.2016
 */
class OrFilter[T](val filters: Filter[T]*) extends Filter[T]({ event => filters.exists { _.includes(event) } })