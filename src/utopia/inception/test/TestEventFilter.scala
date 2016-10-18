package utopia.inception.test

import utopia.inception.event.EventFilter
import utopia.inception.event.Event

class TestEventFilter(val requiredId: Int) extends EventFilter
{
	override def includes(event: Event) = event.identifiers("index") == requiredId
}