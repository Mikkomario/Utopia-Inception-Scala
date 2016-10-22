package utopia.inception.handling

import scala.collection.mutable.ListBuffer
import scala.reflect.ClassTag

/*
object Handler
{
    private val HANDLE = 1
    private val ADD = 2
    private val REMOVE = 3
    private val CLEAR = 4
    
    private val tasks = Array(HANDLE, ADD, REMOVE, CLEAR)
}
* */

/**
 * The different tasks that can be performed on handleable object collections
 */
private object Task extends Enumeration
{
    type Task = Value
    val handle, add, remove = Value
}

/**
 * Handler classes operate over a number of handleable objects, performing some operations on them.
 * Handler is safe to use in multithread environments. Elements can be added and removed even while
 * iterating over the handled instances. Handlers themselves can be handled in order to form
 * hierarchical handling groups.
 * @author Mikko Hilpinen
 * @since 20.10.2016
 */
class Handler[T <: Handleable](val handlerType: HandlerType) extends Handleable
{
    // Handlers have a specific handling state for their own status
    specifyHandlingState(handlerType)
    
    // List for each task target object
    private val taskTargets = Task.values.foldLeft(Map.empty[Task.Value, ListBuffer[T]])(
            {(map, task) => map + (task -> new ListBuffer())});
    // Locks for each task
    private val taskLocks = Task.values.foldLeft(Map.empty[Task.Value, Object])(
            {(map, task) => map + (task -> new AnyRef())});
    
    /**
     * The elements currently handled by this handler
     */
    def toList = list(Task.handle).toList
    
    /**
     * The handler's handling state (whether the handler is being handled or not)
     */
    def handlingState: Boolean = handlingState(handlerType)
    /**
     * Changes this handler's handling state, making it active or disabled (when being handled by
     * another handler)
     */
    def handlingState_=(state: Boolean) = specifyHandlingState(handlerType, state)
    
    /**
     * Adds elements to the handler if they don't exist there already
     * @param elements The elements to be added to this
     */
    def +=(elements: T*) = addToTask(Task.add, elements: _*)
    /**
     * Adds the contents of a collection to this handler
     * @param elements The elements to be added to this
     */
    def ++=(elements: Seq[T]) = this +=(elements: _*)
    
    /**
     * Removes the elements from the handler
     * @param elements The elements to be removed from this
     */
    def -=(elements: T*) = addToTask(Task.remove, elements: _*)
    /**
     * Removes the elements in the provided collection from this handler
     * @param elements The elements to be removed from this
     */
    def --=(elements: Seq[T]) = this -=(elements: _*)
    
    /**
     * Absorbs the contents of another handler, removing the elements from that one and adding them 
     * to this one
     * @param other The handler that is emptied into this one
     */
    def absorb(other: Handler[T]) = 
    {
        // Lists all elements that are going to be moved
        val move = other.list(Task.add) ++ other.list(Task.handle)
        // Removes the elements from the original and adds them to this one
        other --= move
        this ++= move
    }
    
    /**
     * Performs an operation over each of the elements inside this handler
     * @param checkHandlingState Should the call be limited to elements with handling state true
     * @param operation The operation performed over the elements. Returns whether the loop should
     * break (false) or continue (true)
     */
    def foreach(checkHandlingState: Boolean, operation: T => Boolean) = 
    {
        // Updates the status => adds elements and clears removed ones
        moveAll(Task.add, Task.handle)
        val removed = clearTask(Task.remove)
        locked(Task.handle, handleds => handleds --= removed)
        
        // Goes through the handled elements and performs the operation on them.
        var operationQuit = false
        val deadElements = new ListBuffer[T]()
        for (element <- list(Task.handle))
        {
            // Also finds the dead elements and queues them to be removed
            if (element.isDead)
                deadElements += element
            else
            {
                // Operation handling is stopped if on operation returns false
                // Handling state is also checked
                if (!operationQuit && (!checkHandlingState || element.handlingState(handlerType)))
                {
                    if (!operation(element))
                        operationQuit = true
                }
            }
        }
        
        if (!deadElements.isEmpty)
            addToTask(Task.remove, deadElements: _*)
    }
    
    /**
     * Clears the handler of all elements
     */
    def clear() = this --= (list(Task.add) ++ list(Task.handle))
    
    /**
     * Adds a new element to this handler, provided that it's of correct type. If the element is 
     * not of supported type, it won't be added
     * @param element The element that is being added to this handler
     * @return Was the element suitable to be used by this handler
     */
    def unsureAdd(element: Handleable) = 
    {
        // Checks that the element is compatible, then casts it and adds it
    	val B = ClassTag(handlerType.supportedClass)
    			ClassTag(element.getClass) match {
    				case B => this += element.asInstanceOf[T]; true
    				case _ => false
    	}
    }
    
    /**
     * Locks the target list and performs an operation over it. All mutating operations on
     * the lists should be made through this function.
     * @param target The targeted list type
     * @param operation the operation performed over the list
     */
    private def locked(target: Task.Value, operation: (ListBuffer[T]) => Unit) = {
        taskLocks(target).synchronized{operation(taskTargets(target))}}
    
    private def list(target: Task.Value) = 
    {
        val contents = new ListBuffer[T]()
        locked(target, list => contents ++= list)
        contents
    }
    
    private def clearTask(task: Task.Value) = 
    {
        val removed = new ListBuffer[T]()
        locked(task, list => { removed ++= list; list.clear() })
        removed
    }
    
    /**
     * Adds a handled to a task, provided it doesn't exist there already
     * @task The type of task the handled is added to
     * @param elements The elements to be added to the task list
     */
    private def addToTask(task: Task.Value, elements: T*) = 
    {
        locked(task, {list => 
        for (h <- elements)
        {
            if (!list.contains(h)) {
                list += h
            }
        }})
    }
    
    private def moveAll(from: Task.Value, to: Task.Value) = addToTask(to, clearTask(from): _*)
}