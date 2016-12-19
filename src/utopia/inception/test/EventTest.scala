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
	    // XXX Could be a lot better test. Just messing with these here
		// Just testing event immutablility
		
		println("Test started.")
		
		val event1 = new TestEvent(1, "The first event")
		
		println(event1)
		
		var eventVar = event1
		println("Initial event value", eventVar)
		
		eventVar = new TestEvent(2, "different event isntance")
		println("Changed value", eventVar)
		
		
		// Testing event filter(s)
		val filter = new TestEventFilter(1)
		println(filter(event1))
		println(filter(eventVar))
		
		val events = Array[TestEvent](event1, eventVar)
		println(events.filter { event => filter(event) }.size)
		//println(filter(events).size)
		
		
		// Testing combined event filter
		val filter2 = new TestEventFilter(2)
		println(filter2(event1))
		println(filter2(eventVar))
		
		val combinedFilter = filter.or(filter2)
		println(combinedFilter(event1))
		println(combinedFilter(eventVar))
		
		assert(combinedFilter(event1))
		assert(combinedFilter(eventVar))
	}
}