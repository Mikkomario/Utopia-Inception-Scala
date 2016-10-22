package utopia.inception.handling

import scala.collection.immutable.HashMap
import utopia.inception.test.TestHandlerType

/**
 * HandlerRelays are as interfaces for handler groupings. Elements can be added to multiple handlers
 * at once through the relay.
 * @author Mikko Hilpinen
 * @since 22.10.2016
 */
class HandlerRelay
{
    type AnyHandler = Handler[_ <: Handleable]
    
    private var _handlers = new HashMap[HandlerType, AnyHandler]()
    /**
     * The handlers registered into this relay. Immutable.
     * @see register(AnyHandler*)
     */
    def handlers = _handlers;
    
    /**
     * Creates a new relay with existing set of handlers
     * @param handlers The handlers added to the relay
     */
    def this(handlers: Handler[_ <: Handleable]*) = {this; register(handlers: _*)}
    /**
     * Creates a copy of another relay
     * @param other The relay that is being copied
     */
    def this(other: HandlerRelay) = {this; _handlers ++= other._handlers}
    
    /**
     * Adds a number of handlers to this relay. Please note that when overwriting handlers, the 
     * previous handlers will still continue to function when called from elsewhere.
     */
    def register(handlers: AnyHandler*) = {
        _handlers ++= handlers.map { handler => handler.handlerType -> handler }}
    /**
     * Adds a number of handlers to this relay. If any handlers are overwritten, they are killed 
     * so that they won't be handled any longer
     * @param handlers The handlers that are added to this relay
     */
    def replace(handlers: AnyHandler*) =
    {
        val introducedTypes = handlers.map { handler => handler.handlerType }
        this.handlers.filterKeys { typeKey => introducedTypes.contains(typeKey) }.values.foreach {
            handler => handler.kill() }
        register(handlers: _*)
    }
    
    def +=(elements: Handleable*) = {
        handlers.values.foreach { handler => elements.foreach {element => handler.unsureAdd(element) } }}
    
    def -=(handler: AnyHandler) = 
    {
        if (handlers.get(handler.handlerType).forall { existing => existing == handler } )
            _handlers -= handler.handlerType
    }
    
    def setEnabled(enabled: Boolean) = handlers.values.foreach { handler => handler.handlingState = enabled }
}