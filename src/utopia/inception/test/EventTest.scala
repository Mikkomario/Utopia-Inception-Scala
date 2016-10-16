package utopia.inception.test

import utopia.inception.event.Event

/**
 * This test tests the test implementations of the abstract event features introduced in this
 * project
 */
object EventTest
{
	def main(args: Array[String]): Unit = 
	{
		// Just testing event immutablility
		
		println("Test started.")
		
		val event1 = new TestEvent(1, "The first event")
		
		println(event1)
		
		var eventVar = event1
		println("Initial event value", eventVar)
		
		eventVar = new TestEvent(2, "different event isntance")
		println("Changed value", eventVar)
		
		
		// Testing event filter(s)
		val filter = new TestEventFilter()
		println(filter(event1))
		
		val events = Array[TestEvent](event1, eventVar)
		println(events.filter { event => filter(event) }.size)
		println(filter(events).size)
	}
}