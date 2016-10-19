package utopia.inception.handling

import scala.collection.mutable.Map
import scala.collection.mutable.HashMap

/**
 * Handleable objects can be handled by certain type of handler(s)
 * @author Mikko Hilpinen
 * @since 19.10.2016
 */
trait Handleable extends Killable
{
    private val handlingStates = new HashMap[HandlerType, Boolean]()
    
    /**
     * This is the state used when a handling state hasn't been specified for a specific handler
     */
    var defaultHandlingState = false
    
    /**
     * The handling state (should the object be interacted) for a certain handler type
     */
    def handlingState(handlerType: HandlerType) = 
    {
        val specifiedState = handlingStates.get(handlerType)
        
        specifiedState.getOrElse(defaultHandlingState)
    }
    
    /**
     * Specifies the handling state for a specific handler type
     * @param handlerType The type of the handler the state is for
     * @param state The new state for that handler
     * @return Whether there was a specific state defined previously
     */
    def specifyHandlingState(handlerType: HandlerType, state: Boolean) = 
        handlingStates.put(handlerType, state).isDefined
}