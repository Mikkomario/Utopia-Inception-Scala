package utopia.inception.handling

/**
 * Killable objects can be killed, after which they shouldn't be used anymore
 * @author Mikko Hilpinen
 * @since 18.10.2016
 */
trait Mortal
{
    private var _dead = false
    /**
     * The value that defines whether the object should be considered dead or alive. The default 
     * status is alive = false
     */
    def isDead = _dead
    
    /**
     * The object is killed. This change is permanent and cannot be undone.
     */
    def kill() = _dead = true
    
    /*
     * Use this to check in handler if the instance is mortal or immortal
     * def myFunc2[T](t: T) = t match {
	  case _: TraitA => println("A")
	  case _: TraitB => println("B")
	  case _ => println("?")
	}
     */
}