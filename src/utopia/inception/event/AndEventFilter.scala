package utopia.inception.event

/**
 * This event filter only accepts event accepted by all of the included filters
 * @author Mikko Hilpinen
 * @since 21.1.2017
 */
class AndEventFilter[T <: Event](val filters: EventFilter[T]*) extends EventFilter[T](
        { event => filters.forall { _(event) } })