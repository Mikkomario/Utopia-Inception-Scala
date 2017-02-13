package utopia.inception.test

import scala.collection.immutable.HashMap
import utopia.inception.event.Filter

/**
 * This test tests the test implementations of the abstract event features introduced in this
 * project
 */
object EventTest extends App
{   
    // Creates a few test events
    val event1 = new TestEvent(1, "Test1")
    val event2 = new TestEvent(2, "Test2")
    val event3 = new TestEvent(3, "Test3")
    
    // Creates a couple of filters
    val require1 = new TestEventFilter(1)
    val not2 = new Filter[TestEvent]({ _.index != 2 })
    val combo = require1.or(not2)
    
    assert(require1(event1))
    assert(!require1(event2))
    assert(!require1(event3))
    
    assert(not2(event1))
    assert(!not2(event2))
    assert(not2(event3))
    
    assert(combo(event1))
    assert(!combo(event2))
    assert(combo(event3))
    
    println("Success!")
}