package utopia.inception.test

import scala.language.implicitConversions

//import utopia.inception.state.ImmutableStateReference.booleanToState
import utopia.inception.state.StateReference
import utopia.inception.state.ImmutableStateReference

object StateTest
{
    //implicit def booleanToState(b: Boolean) = new ImmutableStateReference(b)  
    
    def main(args: Array[String]): Unit = 
	{
        val stateRef = new ImmutableStateReference(true)
        
        if (stateRef)
        {
            println("Stateref can be used as a boolean")
        }
        
        // TIDI
        //println(ImmutableStateReference.test)
        
        testMethodForState(false)
	}
    
    def testMethodForState(state: ImmutableStateReference) = println(s"State is ${state.state}")
}