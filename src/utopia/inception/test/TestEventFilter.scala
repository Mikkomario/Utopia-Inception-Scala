package utopia.inception.test

import utopia.inception.event.EventFilter
import utopia.inception.event.Event

class TestEventFilter[T <: Event](val requiredId: Int) extends EventFilter[T](
        { event => event.identifiers("index") == requiredId })