package utopia.inception.test

import utopia.inception.event.Event
import utopia.inception.event.ConditionEventFilter
import scala.collection.immutable.HashMap
import utopia.flow.datastructure.immutable.Value
import utopia.flow.generic.DataType

/**
 * This test tests the test implementations of the abstract event features introduced in this
 * project
 */
object EventTest extends App
{
    DataType.setup()
    
    // Creates a few test events
    val event1 = new TestEvent(1, "Test1")
    val event2 = new TestEvent(2, "Test2")
    val event3 = new TestEvent(3, "Test3")
    
    assert(event1.identifiers.attributes.size == 2)
    
    // Creates a couple of filters
    val require1 = new ConditionEventFilter[Event](HashMap("index" -> Value.of(1)))
    val not2 = new ConditionEventFilter[Event](HashMap(), HashMap("index" -> Value.of(2)))
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