package utopia.inception.event

import scala.collection.mutable.ArrayBuffer

/**
 * These event filters go through an event's identifiers and check whether required or forbidden
 * identifiers exist.
 * @author Mikko Hilpinen
 * @since 17.10.2016
 */
class ConditionEventFilter(val requiredIdentifiers: ArrayBuffer[Any] = ArrayBuffer[Any](), 
		val forbiddenFeatures: ArrayBuffer[Any] = ArrayBuffer[Any]()) extends EventFilter
{	
	// IMPLEMENTED METHODS	-------
	
	override def includes(event: Event): Boolean = 
	{
		// All required features must exist and none of the forbidden features may exist
		event.identifiers.forall { identifier => requiredIdentifiers.contains(identifier) && 
		    !forbiddenFeatures.contains(identifier) }
	}
}