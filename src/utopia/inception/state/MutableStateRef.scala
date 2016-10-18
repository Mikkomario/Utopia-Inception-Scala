package utopia.inception.state

/**
 * These state references are mutable
 * @author Mikko Hilpinen
 * @since 18.10.2016
 */
@deprecated
class MutableStateRef(var state: Boolean) extends StateRef
{
    // TODO: Add state change listening
}