package utopia.inception.test

import scala.reflect.ClassTag
import utopia.inception.handling.HandlerRelay
import utopia.inception.handling.Handler
import utopia.inception.handling.mutable.{Handleable, Handler, HandlerRelay}

object HandlingTest extends App
{
    def countHandedObjects(handler: Handler[_]) = 
    {
        var amount = 0
        handler.foreach(true, { _ => amount += 1; true })
        amount
    }
    
    val handlerType = TestHandlerType
    
    val o1 = new TestObject(1)
    val o2 = new TestObject(2)
    val o3 = new TestObject(3)
    
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
    
    handler1 ++= (o2, o3, o1)
    
    handler1.sortWith({ _.index <= _.index })
    assert(handler1.elements.head == o1)
    handler1.sortWith({ _.index >= _.index })
    assert(handler1.elements.head == o3)
    
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