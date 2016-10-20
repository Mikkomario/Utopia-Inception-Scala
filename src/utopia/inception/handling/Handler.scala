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
    val handle, add, clear, remove = Value
}

/**
 * Handler classes operate over a number of handleable objects, performing some operations on them.
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
    
    // XXX: May add this but will cost in performance
    def toList = taskTargets(Task.handle).toList // ++ taskTargets(Task.add)
    
    /**
     * Adds a handled to the handler if it doesn't exist there already
     */
    def add(handleable: T) = addToTask(handleable, Task.add)
    def +(handleable: T) = add(handleable)
    
    /*
    def absorb(other: Handler[T]) = 
    {
        
    }*/
    
    /**
     * Locks the target list and performs an operation over it. All mutating operations on
     * the lists should be made through this function.
     * @param target The targeted list type
     * @param operation the operation performed over the list
     */
    private def locked(target: Task.Value, operation: (ListBuffer[T]) => Unit) = {
        taskLocks(target).synchronized{operation(taskTargets(target))}}
    
    /**
     * Adds a handled to a task, provided it doesn't exist there already
     * @param h The handled to be added to the task list
     * @task The type of task the handled is added to
     */
    private def addToTask(h: T, task: Task.Value) = locked(task, {list => 
            if (list.contains(h))
            {
                list += h
            }})
}