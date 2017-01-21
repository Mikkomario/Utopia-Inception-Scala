package utopia.inception.event

/**
 * This event filter will accept any kind of event
 * @author Mikko Hilpinen
 * @since 21.1.2017
 */
class AnyEventFilter[T <: Event] extends EventFilter[T]({ _ => true })