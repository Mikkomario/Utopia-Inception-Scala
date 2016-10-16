package utopia.inception.test

import utopia.inception.event.EventFilter
import utopia.inception.event.Event

class TestEventFilter extends EventFilter
{
	def includes(event: Event): Boolean = true
}