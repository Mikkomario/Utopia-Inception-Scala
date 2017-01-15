package utopia.inception.event

import utopia.flow.datastructure.immutable.Value
import scala.collection.immutable.HashMap

/**
 * These event filters go through an event's identifiers and check whether required or forbidden
 * identifiers exist.
 * @author Mikko Hilpinen
 * @since 17.10.2016
 */
class ConditionEventFilter[T <: Event](val required: Map[String, Value] = HashMap(), 
		val forbidden: Map[String, Value] = HashMap()) extends EventFilter[T]({ event => 
	    required.forall { case (name, value) => event.identifiers(name) == value } && 
	    forbidden.forall { case (name, value) => event.identifiers(name) != value}})