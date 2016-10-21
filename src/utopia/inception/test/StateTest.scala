package utopia.inception.test

//import utopia.inception.state.ImmutableStateReference.booleanToState
import utopia.inception.state.StateRef
import utopia.inception.state.ImmutableStateRef

@deprecated
object StateTest
{
    //implicit def booleanToState(b: Boolean) = new ImmutableStateReference(b)  
    
    def main(args: Array[String]): Unit = 
	{
        val stateRef = new ImmutableStateRef(true)
        
        if (stateRef)
        {
            println("Stateref can be used as a boolean")
        }
        
        testMethodForState(false)
	}
    
    def testMethodForState(state: ImmutableStateRef) = println(s"State is ${state.state}")
}