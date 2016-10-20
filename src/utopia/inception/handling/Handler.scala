package utopia.inception.handling

import scala.collection.mutable.ListBuffer

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
 * iterating over the handled instances.
 * @author Mikko Hilpinen
 * @since 20.10.2016
 */
class Handler[T <: Handleable]
{
    // List for each task target object
    private val taskTargets = Task.values.foldLeft(Map.empty[Task.Value, ListBuffer[T]])(
            {(map, task) => map + (task -> new ListBuffer())});
    // Locks for each task
    private val taskLocks = Task.values.foldLeft(Map.empty[Task.Value, Object])(
            {(map, task) => map + (task -> new AnyRef())});
    
    // XXX: May add this but will cost in performance. Also, may want to use lock here.
    def toList = taskTargets(Task.handle).toList // ++ taskTargets(Task.add)
    
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
    
    def absorb(other: Handler[T]) = 
    {
        // Lists all elements that are going to be moved
        val move = other.list(Task.add) ++ other.list(Task.handle)
        // Removes the elements from the original and adds them to this one
        other --= move
        this ++= move
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
    
    private def moveAll(from: Task.Value, to: Task.Value) = 
    {
        // Removes all instances from the source list
        val removed = new ListBuffer[T]()
        locked(from, { fromList => removed ++= fromList; fromList.clear() });
        
        // Inserts the removed elements to the target list
        locked(to, { toList => toList ++= removed })
    }
}