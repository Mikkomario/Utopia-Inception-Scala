package utopia.inception.handling

/**
 * There are different handlers with different functions. Each handler type supports objects of 
 * certain type
 * @author Mikko Hilpinen
 * @since 19.10.2016
 */
trait HandlerType
{
    def supportedClass: Class[_]
}