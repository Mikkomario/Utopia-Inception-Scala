package utopia.inception.handling

import scala.reflect.ClassTag

/**
 * There are different handlers with different functions. Each handler type supports objects of 
 * certain type
 * @author Mikko Hilpinen
 * @since 19.10.2016
 */
class HandlerType(val supportedClass: Class[_])
{
   /**
     * Checks whether the provided instance is supported by this handler type.
     * If returns true, it is safe to cast the element into that supported class.
     * @return is the provided element an instance of the class supported by this handler type
     */
    def supportsInstance(element: Handleable) = 
    {
        val B = ClassTag(supportedClass)
    			ClassTag(element.getClass) match {
    				case B => true
    				case _ => false
    	}
    }
}