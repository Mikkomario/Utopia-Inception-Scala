package utopia.inception.event

import scala.collection.mutable.ArrayBuffer

/**
 * These event filters go through an event's identifiers and check whether required or forbidden
 * identifiers exist.
 * @author Mikko Hilpinen
 * @since 17.10.2016
 */
class ConditionEventFilter(val required: Vector[Any] = Vector[Any](), 
		val forbidden: Vector[Any] = Vector[Any]()) extends EventFilter
{	
	// IMPLEMENTED METHODS	-------
	
	override def includes(event: Event): Boolean = 
	{
	    // All of the required features must exist. None of the forbidden may
	    // FIXME: There is a very high chance this is broken
	    
		// All required features must exist and none of the forbidden features may exist
		event.identifiers.forall { identifier => required.contains(identifier) && 
		    !forbidden.contains(identifier) }
	}
}