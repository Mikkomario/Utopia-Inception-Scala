package utopia.inception.handling.mutable

import utopia.inception.handling
import utopia.inception.handling.HandlerType

/**
  * DeepHandler is a common implementation for Handlers that also act as Handleable items. Deep Handlers are completely
  * mutable. This class resembles the mutable v1 Handler.
  * @param handlerType The type of this handler
  * @param initialElements The elements initially placed in this handler
  * @tparam A The type of object handled by this handler
  */
class DeepHandler[A <: handling.Handleable](handlerType: HandlerType, initialElements: TraversableOnce[A])
	extends Handler[A](handlerType, initialElements) with Handleable with Killable
{
	// INITIAL CODE	----------------
	
	// Specifies handler's own handling state
	specifyHandlingState(handlerType)
	
	
	// COMPUTED	--------------------
	
	/**
	  * @return Whether this handler should be handled by handlers of its own type
	  */
	def handlingState = handlingState(handlerType)
	
	/**
	  * Changes handling state
	  * @param newState Whether this handler should be handled by handlers of its own type
	  */
	def handlingState_=(newState: Boolean) = specifyHandlingState(handlerType, newState)
}
