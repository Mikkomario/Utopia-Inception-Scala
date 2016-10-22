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
    
    def this(handlers: Handler[_ <: Handleable]*) = {this; register(handlers: _*)}
    
    def register(handlers: AnyHandler*) = {
        _handlers ++= handlers.map { handler => handler.handlerType -> handler }}
    
    /*
    def register(handlers: Handler[_]*) = 
    {
        _handlers = _handlers ++ handlers.map { (handler => handler.handlerType -> handler) }
    }*/
}