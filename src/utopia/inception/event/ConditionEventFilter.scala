package utopia.inception.event

import utopia.flow.datastructure.immutable.Value
import scala.collection.immutable.HashMap

/**
 * These event filters go through an event's identifiers and check whether required or forbidden
 * identifiers exist.
 * @author Mikko Hilpinen
 * @since 17.10.2016
 */
class ConditionEventFilter(val required: Map[String, Value] = HashMap(), 
		val forbidden: Map[String, Value] = HashMap()) extends EventFilter
{	
	// IMPLEMENTED METHODS	-------
	
    // All of the required identifiers must exist. None of the required identifiers may exist
	override def includes(event: Event): Boolean = 
	    required.forall { case (name, value) => event.identifiers(name) == value } && 
	    forbidden.forall { case (name, value) => event.identifiers(name) != value}
	
}