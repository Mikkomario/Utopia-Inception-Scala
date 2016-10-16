package utopia.inception.event

/**
 * Event is a general trait implemented by all different events. Events can be distinguished from
 * other events based on their identifiers.
 * 
 * Events should always be immutable
 */
trait Event
{
	// REQUIRED ATTRIBUTES	--------
	
	val identifiers: Array[Any]
	
	
	// IMPLEMENTATIONS	------------
	
	override def toString() = 
	{
		var s = new StringBuilder()
		this.identifiers.headOption.foreach { firstElement => s.append(firstElement.toString()) }
		this.identifiers.tail.foreach {
			identifier =>
				s.append(", ")
				s.append(identifier.toString())
		}
		
		s.toString()
	}
}