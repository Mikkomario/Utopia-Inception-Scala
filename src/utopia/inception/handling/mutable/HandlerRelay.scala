package utopia.inception.handling.mutable

object HandlerRelay
{
    /**
      * @return A new empty relay
      */
    def apply() = new HandlerRelay(Vector())
    
    /**
      * @param handlers Handlers
      * @return A relay with specified handlers
      */
    def apply(handlers: TraversableOnce[Handler[_]]) = new HandlerRelay(handlers)
    
    /**
      * @param handler A handler
      * @return A relay with the provided handler
      */
    def apply(handler: Handler[_]) = new HandlerRelay(Vector(handler))
    
    /**
      * @param first A handler
      * @param second Another handler
      * @param more More handlers
      * @return A relay with all the provided handlers
      */
    def apply(first: Handler[_], second: Handler[_], more: Handler[_]*) = new HandlerRelay(Vector(first, second) ++ more)
}

/**
 * HandlerRelays are as interfaces for handler groupings. Elements can be added to multiple handlers
 * at once through a relay.
 * @author Mikko Hilpinen
 * @since 22.10.2016
 */
class HandlerRelay(initialHandlers: TraversableOnce[Handler[_]])
{
    // ATTRIBUTES    --------------
    
    private var _handlers = initialHandlers.map { h => h.handlerType -> h }.toMap
    
    /**
      * @return A mapping of handlers, each tied to their handler type
      */
    def handlerMap = _handlers
    
    /**
     * The handlers registered into this relay.
     * @see register(Handler)
     */
    def handlers = _handlers.values
    
    
    // OPERATORS    --------------
    
    /**
     * Adds a single element to the suitable handlers in this relay
     * @param element the element that is added
     */
    def +=(element: Handleable) = handlers.foreach { _ ?+= element }
    
    /**
     * Adds a number of elements to suitable handlers in this relay
     * @param elements The elements to be added
     */
    def ++=(elements: TraversableOnce[Handleable]) = elements.foreach(+=)
    
    /**
     * Adds two or more elements to suitable handlers in this relay
     */
    def ++=(first: Handleable, second: Handleable, more: Handleable*): Unit = this ++= (Vector(first, second) ++ more)
    
    /**
     * Removes an element from each handler in this relay
     */
    def -=(element: Handleable) = handlers.foreach { _ -= element }
    
    /**
     * Removes multiple elements from each handler in this relay
     */
    def --=(elements: Traversable[Handleable]) = handlers.foreach { _ --= elements }
    
    /**
     * Removes two or more elements from the handlers in this relay
     */
    def --=(first: Handleable, second: Handleable, more: Handleable*): Unit = this --= (Vector(first, second) ++ more)
    
    
    // OTHER    -------------------
    
    /**
      * @return A copy of this relay with shared handlers
      */
    def copy() = new HandlerRelay(handlers)
    
    /**
      * Registers a new handler to this relay. If there already exists another handler with the same handler type,
      * that will be replaced with this new one
      * @param handler A new handler
      */
    def register(handler: Handler[_]) = _handlers += handler.handlerType -> handler
    
    def register(handlers: TraversableOnce[Handler[_]]) = _handlers ++= handlers.map { h => h.handlerType -> h }
    
    def register(first: Handler[_], second: Handler[_], more: Handler[_]*): Unit = register(Vector(first, second) ++ more)
    
    def remove(handler: Handler[_]) = _handlers = _handlers.filterNot { case (_, existing) => existing == handler }
    
    def remove(handlers: Traversable[Handler[_]]) = _handlers = _handlers.filterNot {
            case (_, existing) => handlers.exists { _ == existing } }
}