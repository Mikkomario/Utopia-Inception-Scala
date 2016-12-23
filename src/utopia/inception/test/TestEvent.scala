package utopia.inception.test

import utopia.inception.event.Event
import utopia.flow.datastructure.immutable.Constant
import utopia.flow.datastructure.immutable.Value
import utopia.flow.datastructure.immutable.Model

/**
 * This is a test implementation of the Event trait
 */
class TestEvent(val index: Int, val message: String) extends Event
{
	val identifiers = 
	{
	    val properties = Vector(new Constant("index", Value of index), 
	            new Constant("message", Value of message))
	    
	    new Model(properties)        
	}
}