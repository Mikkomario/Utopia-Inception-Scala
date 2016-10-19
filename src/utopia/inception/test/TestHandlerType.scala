package utopia.inception.test

import utopia.inception.handling.HandlerType

object TestHandlerType extends HandlerType
{
    val supportedClass = classOf[TestObject]
    def instance = this
}