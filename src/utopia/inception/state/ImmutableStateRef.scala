package utopia.inception.state

import scala.language.implicitConversions

object ImmutableStateRef
{
    // Boolean values can be used as immutable state references
    implicit def booleanToState(b: Boolean) = new ImmutableStateRef(b)
}

/**
 * Immutable state references refer an immutable state. They are very simple and mostly used as
 * wrappers for booleans
 * @author Mikko Hilpinen
 * @since 18.10.2016
 */
@deprecated
class ImmutableStateRef(val state: Boolean) extends StateRef
{
    // No further implementation
}