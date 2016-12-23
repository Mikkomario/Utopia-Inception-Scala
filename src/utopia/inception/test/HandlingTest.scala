package utopia.inception.test

import scala.reflect.ClassTag
import utopia.inception.handling.Handleable
import utopia.inception.handling.HandlerRelay
import utopia.inception.handling.Handler

object HandlingTest extends App
{
    def countHandedObjects(handler: Handler[_]) = 
    {
        var amount = 0
        handler.foreach(true, { _ => amount += 1; true })
        amount
    }
    
    val handlerType = TestHandlerType
    
    val o1 = new TestObject()
    val o2 = new TestObject()
    val o3 = new TestObject()
    
    assert(!o1.isDead)
    assert(o1.defaultHandlingState)
    assert(o1.handlingState(handlerType))
    
    o1.specifyHandlingState(handlerType, false)
    assert(!o1.handlingState(handlerType))
    assert(o1.defaultHandlingState)
    
    val handler1 = new Handler[TestObject](handlerType)
    val relay = new HandlerRelay(handler1)
    
    assert(handler1.handlingState)
    assert(!relay.handlers.isEmpty)
    assert(handler1.elements.isEmpty)
    
    handler1 ++= (o1, o2, o3)
    
    assert(handler1.elements.size == 3)
    assert(countHandedObjects(handler1) == 2)
    
    o1.specifyHandlingState(handlerType, true)
    assert(countHandedObjects(handler1) == 3)
    
    o1.defaultHandlingState = false
    assert(countHandedObjects(handler1) == 3)
    
    o2.defaultHandlingState = false
    assert(countHandedObjects(handler1) == 2)
    
    o1.kill()
    assert(countHandedObjects(handler1) == 1)
    assert(handler1.elements.size == 2)
    
    println("Success!")
}