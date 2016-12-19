package utopia.inception.event

import utopia.flow.datastructure.immutable.Model
import utopia.flow.datastructure.immutable.Constant

/**
 * Event is a general trait implemented by all different events. Events can be distinguished from
 * other events based on their identifiers. 
 * Events should always be immutable and have value semantics
 * @author Mikko Hilpinen
 * @since 16.10.2016
 */
trait Event
{
	// REQUIRED ATTRIBUTES	--------
	
	val identifiers: Model[Constant]
	
	
	// IMPLEMENTATIONS	------------
	
	/**
	 * @return A string version of the event's identifiers
	 */
	override def toString() = identifiers.toString()
}