package utopia.inception.test

import utopia.inception.util.Filter

class TestEventFilter(val requiredId: Int) extends Filter[TestEvent]({ _.index == requiredId })