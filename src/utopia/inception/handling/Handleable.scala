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
     * The object this handleable depends from. The object's handling state must be true in order 
     * for this object to be handled.
     */
    var dependsFrom: Option[Handleable] = None
    
    /**
     * This is the state used when a handling state hasn't been specified for a specific handler
     */
    var defaultHandlingState = true
    
    /**
     * The handling state (should the object be interacted) for a certain handler type
     */
    def handlingState(handlerType: HandlerType): Boolean = 
    {
        // If dependent object is specified and its state is false, can't have true state
        if (dependsFrom.isDefined && !dependsFrom.get.handlingState(handlerType))
            return false;
        else
        {
            val specifiedState = handlingStates.get(handlerType)
            specifiedState.getOrElse(defaultHandlingState)
        }
    }
    
    /**
     * Specifies the handling state for a specific handler type
     * @param handlerType The type of the handler the state is for
     * @param state The new state for that handler
     * @return Whether there was a specific state defined previously
     */
    def specifyHandlingState(handlerType: HandlerType, state: Boolean) = 
        handlingStates.put(handlerType, state).isDefined;
    
    /**
     * Specifies the handling state for a specific handler type so that the default state is 
     * no longer used. The handling state stays the same until it is changed, however.
     * @param handlerType the handlerType for which the state is specified
     * @return whether an existing specification was overwritten
     */
    def specifyHandlingState(handlerType: HandlerType): Boolean = 
        specifyHandlingState(handlerType, handlingState(handlerType));
}