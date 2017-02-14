package utopia.inception.event

/**
 * Event filters are used for filtering elements that are of importance to the user. Functions 
 * usually work better in simple use cases but Filter instances may be used in more dynamic
 * solutions.
 * @author Mikko Hilpinen
 * @since 16.10.2016
 */
class Filter[T](val includes: T => Boolean)
{
	// OPERATORS	-------------
	
	/**
	 * Applies 'this' filter over the 'event'
	 * @param event the event that is being evaluated
	 * @return does the filter accept / include 'element'
	 */
	def apply(event: T) = includes(event)
	
	
	// OTHER METHODS	---------
	
	/**
	 * Combines a set of filters into a combined filter. The filter will include the event if any of
	 * the provided filters include the event
	 * @param others The filters combined with 'this' to form a new filter
	 * @return The combined filter
	 */
	def or(others: Filter[T]*) =
	{
	    val filters = Vector(this) ++: others
	    new OrFilter(filters: _*)
	}
}