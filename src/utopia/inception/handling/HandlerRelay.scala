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
    // TYPES    -------------------
    
    type AnyHandler = Handler[_ <: Handleable]
    
    
    // ATTRIBUTES    --------------
    
    private var _handlers = new HashMap[HandlerType, AnyHandler]()
    /**
     * The handlers registered into this relay. Immutable.
     * @see register(AnyHandler*)
     */
    def handlers = _handlers;
    
    
    // CONSTRUCTOR OVERLOAD    ---
    
    /**
     * Creates a new relay with existing set of handlers
     * @param handlers The handlers added to the relay
     */
    def this(handlers: Handler[_ <: Handleable]*) = {this; register(handlers: _*)}
    /**
     * Creates a copy of another relay. The referenced handlers are not copied, however.
     * @param other The relay that is being copied
     */
    def this(other: HandlerRelay) = {this; _handlers ++= other._handlers}
    
    
    // OPERATORS    --------------
    
    /**
     * Adds a single element to the suitable handlers in this relay
     * @param element the element that is added
     */
    def +=(element: Handleable) = handlers.values.foreach { _ ?+= element }
    
    /**
     * Adds a number of elements to suitable handlers in this relay
     * @param elements The elements to be added
     */
    def ++=(elements: Traversable[Handleable]) = handlers.values.foreach { handler => 
            elements.foreach { handler ?+= _ } }
    
    /**
     * Adds two or more elements to suitable handlers in this relay
     */
    def ++=(first: Handleable, second: Handleable, more: Handleable*): Unit = this ++= more :+ first :+ second
    
    /**
     * Removes an element from each handler in this relay
     */
    def -=(element: Handleable) = handlers.values.foreach { _ -= element }
    
    /**
     * Removes multiple elements from each handler in this relay
     */
    def --=(elements: Traversable[Handleable]) = handlers.values.foreach { _ --= elements }
    
    /**
     * Removes two or more elements from the handlers in this relay
     */
    def --=(first: Handleable, second: Handleable, more: Handleable*): Unit = this --= more :+ first :+ second
    
    
    // OTHER METHODS    ----------
    
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
    
    /**
     * Removes one or more handlers from the handlers registered to this relay
     */
    def remove(handlers: AnyHandler*) = _handlers = _handlers.filterNot { case (_, handler) => 
            handlers.exists { _ == handler } }
    
    /**
     * Changes the handling state of each of the handlers in this relay
     * @param enabled The new handling state for the handlers
     */
    def setEnabled(enabled: Boolean) = handlers.values.foreach { 
        handler => handler.handlingState = enabled };
}