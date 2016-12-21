package utopia.inception.handling

/**
 * Handler classes operate over a number of handleable objects, performing some operations on them.
 * Handler is safe to use in multithread environments. Elements can be added and removed while
 * iterating over the handled instances. Handlers themselves can be handled in order to form
 * hierarchical handling groups.
 * @author Mikko Hilpinen
 * @since 20.10.2016 (Rewritten: 21.12.2016)
 */
class Handler[T <: Handleable](val handlerType: HandlerType) extends Handleable
{
    // INITIAL CODE    ---------------
    
    // Handlers have a specific handling state for their own status
    specifyHandlingState(handlerType)
    
    private var _elements = Vector[T]()
    /**
     * The elements handled by this handler
     */
    def elements = _elements
    
    
    // CONSTRUCTOR OVERLOAD    ------
    
    def this(handlerType: HandlerType, elements: T*) = { this(handlerType); this ++= elements }
    
    
    // HANDLEABLE INTERFACE    -----
    
    /**
     * The handler's handling state (whether the handler is being handled or not)
     */
    def handlingState: Boolean = handlingState(handlerType)
    /**
     * Changes this handler's handling state, making it active or disabled (when being handled by
     * another handler)
     */
    def handlingState_=(state: Boolean) = specifyHandlingState(handlerType, state)
    
    
    // OPERATORS    -----------------
    
    /**
     * Adds a new element to the handler if they don't exist there already
     * @param elements The elements to be added to this handler
     */
    def +=(element: T) = if (!elements.contains(element)) _elements :+= element
    /**
     * Adds the contents of a collection to this handler
     * @param elements The elements to be added to this handler
     */
    def ++=(added: Traversable[T]) = _elements ++= added.filterNot { elements.contains(_) }
    /**
     * Adds two or more elements to this handler
     */
    def ++=(first: T, second: T, more: T*): Unit = this ++= more :+ first :+ second
    
    /**
     * Removes an element from the handler
     * @param elements The element to be removed from this handler
     */
    def -=(element: Handleable): Unit = _elements = elements.filterNot { _ == element }
    /**
     * Removes the provided elements from this handler
     * @param elements The elements to be removed from this handler
     */
    def --=(removed: Traversable[Handleable]) = _elements = elements.filterNot { 
        element => removed.exists { _ == element } }
    /**
     * Removes two or more elements from this handler
     */
    def --=(first: Handleable, second: Handleable, more: Handleable*): Unit = this --= more :+ first :+ second
    
    /**
     * Adds a new element to this handler, provided that it's of correct type. If the element is 
     * not of supported type, it won't be added
     * @param element The element that is being added to this handler
     * @return Was the element suitable to be used by this handler
     */
    def ?+=(element: Handleable) = 
    {
        if (handlerType.supportsInstance(element))
        {
            this += element.asInstanceOf[T]
            true
        }
        else
            false
    }
    
    
    // OTHER METHODS    ----------
    
    /**
     * Performs an operation over each of the elements inside this handler
     * @param checkHandlingState Should the call be limited to elements with handling state true
     * @param operation The operation performed over the elements. Returns whether the loop should
     * break (false) or continue (true)
     */
    def foreach(checkHandlingState: Boolean, operation: T => Boolean) = 
    {
        // Clears the dead elements first
        _elements = elements.filterNot { _.isDead }
        
        // Operates on the elements until the sequence is broken or elements end
        elements.find { element => element.handlingState(handlerType) && !operation(element) }
    }
    
    /**
     * Clears the handler of all elements
     */
    def clear() = _elements = Vector()
    
    /**
     * Absorbs the contents of another handler, removing the elements from that one and adding them 
     * to this one
     * @param other The handler that is emptied into this one
     */
    def absorb(other: Handler[T]) = 
    {
        val moved = other.elements
        other --= moved
        this ++= moved
    }
}