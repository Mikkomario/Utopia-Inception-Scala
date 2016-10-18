package utopia.inception.state

import scala.language.implicitConversions

object ImmutableStateReference
{
    // Boolean values can be used as immutable state references
    implicit def booleanToState(b: Boolean) = new ImmutableStateReference(b)
}

/**
 * Immutable state references refer an immutable state. They are very simple and mostly used as
 * wrappers for booleans
 * @author Mikko Hilpinen
 * @since 18.10.2016
 */
class ImmutableStateReference(val state: Boolean) extends StateReference
{
    // No further implementation
}