package utopia.inception.util

/**
 * This event filter will accept any kind of event
 * @author Mikko Hilpinen
 * @since 21.1.2017
 */
class AnyFilter[T] extends Filter[T]({ _ => true })