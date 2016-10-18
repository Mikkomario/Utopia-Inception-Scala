package utopia.inception.state

import scala.language.implicitConversions

object StateReference
{
    // State references can implicitly be converted to booleans
    implicit def stateToBoolean(state: StateReference) = state.state
}

/**
 * States are basically boolean values which can be tracked and referenced. Some states are mutable 
 * while others are not
 * @author Mikko Hilpinen
 * @since 18.10.2016
 */
trait StateReference
{
    /**
     * The current status of the state (true or false)
     */
    def state: Boolean
}