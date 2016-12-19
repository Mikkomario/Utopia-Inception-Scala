package utopia.inception.event

/**
 * Event is a general trait implemented by all different events. Events can be distinguished from
 * other events based on their identifiers.
 * 
 * Events should always be immutable
 * @author Mikko Hilpinen
 * @since 16.10.2016
 */
trait Event
{
	// REQUIRED ATTRIBUTES	--------
	
	val identifiers: Map[String, Any]
	
	
	// IMPLEMENTATIONS	------------
	
	/**
	 * @return A string version of the event's identifiers
	 */
	override def toString() = 
	{
		var s = new StringBuilder()
		
		if (!identifiers.isEmpty)
		{
		    this.identifiers.headOption.foreach { case (id, value) => s.append(s"$id = $value")}
		    this.identifiers.tail.foreach { case (id, value) => s.append(s", $id = $value") }
		}
		
		s.toString()
	}
}