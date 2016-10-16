package utopia.inception.event

import scala.reflect.ClassTag

/**
 * Event filters are used for filtering events that are of some importance to the user.
 * @author Mikko Hilpinen
 * @since 16.10.2016
 */
trait EventFilter
{
	// REQUIRED METHODS	---------
	
	/**
	 * Checks whether the filter would include the provided event
	 * @return Does the filter include the event
	 */
	def includes(event: Event): Boolean
	
	
	// OPERATORS	-------------
	
	/**
	 * Applies 'this' filter over the 'event'
	 * @param event the event that is being evaluated
	 * @return does the filter accept / include 'element'
	 */
	def apply(event: Event) = includes(event)
	
	
	// OTHER METHODS	---------
	
	/**
	 * Filters a collection of events and only includes the element accepted by this filter.
	 * @param array The array that is filtered
	 * @return The elements of the 'array' included by 'this'
	 */
	def apply[E <: Event](array: Traversable[E])(implicit tag: ClassTag[E]): Traversable[E] =
	{
		array.filter { event => includes(event) }
	}
}