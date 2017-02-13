package utopia.inception.test

import utopia.inception.event.Filter

class TestEventFilter(val requiredId: Int) extends Filter[TestEvent]({ _.index == requiredId })