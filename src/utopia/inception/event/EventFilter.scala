package utopia.inception.event

import scala.reflect.ClassTag
import scala.collection.mutable.ArrayBuffer

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
	
	/**
	 * Combines a set of filters into a combined filter. The filter will include the event if any of
	 * the provided filters include the event
	 * @param others The filters combined with 'this' to form a new filter
	 * @return The combined filter
	 */
	def or(others: EventFilter*) =
	{
	    val filters = ArrayBuffer(this)
	    filters.append(others: _*)
	    
	    new CombinedEventFilter(filters: _*)
	}
}