package utopia.inception.test

import utopia.inception.event.Event

/**
 * This is a test implementation of the Event trait
 */
class TestEvent(val index: Int, val message: String) extends Event
{
	val identifiers = Array(index, message)
}