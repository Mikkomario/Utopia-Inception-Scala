package utopia.inception.handling

import scala.collection.immutable.HashMap

/**
 * Handleable objects can be handled by certain type of handler(s)
 * @author Mikko Hilpinen
 * @since 19.10.2016
 */
trait Handleable extends Mortal
{
    private var _handlingStates = new HashMap[HandlerType, Boolean]()
    /**
     * The specified handling states for this handleable instance
     */
    def handlingStates = _handlingStates
    
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
        if (dependsFrom.exists { !_.handlingState(handlerType) })
            false;
        else
            handlingStates.get(handlerType).getOrElse(defaultHandlingState)
    }
    
    /**
     * Specifies the handling state for a specific handler type
     * @param handlerType The type of the handler the state is for
     * @param state The new state for that handler
     */
    def specifyHandlingState(handlerType: HandlerType, state: Boolean) = 
            _handlingStates += handlerType -> state;
    
    /**
     * Specifies the handling state for a specific handler type so that the default state is 
     * no longer used. The handling state stays the same until it is changed, however.
     * @param handlerType the handlerType for which the state is specified
     */
    def specifyHandlingState(handlerType: HandlerType): Unit = 
        specifyHandlingState(handlerType, handlingState(handlerType));
}