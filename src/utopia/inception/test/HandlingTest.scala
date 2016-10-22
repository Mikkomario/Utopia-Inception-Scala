package utopia.inception.test

import scala.reflect.ClassTag
import utopia.inception.handling.Handler
import utopia.inception.handling.Handleable

object HandlingTest
{
    def main(args: Array[String]): Unit = 
	{
        // Testing class functions
        val testObject = "Test"
        
        val acceptedClass: Class[_] = classOf[String]
        val acceptedType = ClassTag(classOf[String])
        
        assert(valueIsOfClass(testObject, "asd"))
        /*
        ClassTag(testObject.getClass) match
        {
            case accepted: acceptedType => println("It is accepted")
            case _ => println("it is not")
        }*/
        
        println(test2("asd", 3))
        println(isString("MOI"))
        
        val obj: Any = "asd"
        //val str = obj asInstanceOf[classOf[String]]
        //type
        //TypeTag(
        println(acceptedClass.cast(obj))
        
        
        // Tests basic handleable functions
        testHandleable()
        testHandler()
	}
    
    def testHandleable() = 
    {
        val test = new TestObject()
        
        assert(!test.isDead)
        assert(test.defaultHandlingState)
        assert(test.handlingState(TestHandlerType.instance))
        
        test.specifyHandlingState(TestHandlerType.instance)
        test.defaultHandlingState = false
        assert(!test.defaultHandlingState)
        assert(test.handlingState(TestHandlerType.instance))
        
        val test2 = new TestObject()
        test2.dependsFrom = Option(test)
        assert(test2.handlingState(TestHandlerType.instance))
        
        test.specifyHandlingState(TestHandlerType.instance, false)
        assert(!test2.handlingState(TestHandlerType.instance))
        
        test.specifyHandlingState(TestHandlerType.instance, true)
        test2.defaultHandlingState = false
        assert(!test2.handlingState(TestHandlerType.instance))
        
        println("success")
    }
    
    def testHandler() = 
    {
        println("Testing handler")
        val handler = new Handler[TestObject](TestHandlerType.instance)
        
        val testObject1 = new TestObject()
        val testObject2 = new TestObject()
        val testObject3 = new TestObject()
        
        handler += (testObject1, testObject2, testObject3)
        
        testObject1.defaultHandlingState = false
        handler.foreach(true, print)
        println("---")
        handler.foreach(false, print)
        println("---")
        handler.foreach(false, printFirst)
        println("---")
        
        testObject1.kill()
        handler.foreach(false, print)
        println("---")
        
        val handler2 = new Handler[TestObject](TestHandlerType.instance)
        handler2 absorb handler
        println("")
        handler.foreach(false, print)
        println("---")
        handler2.foreach(false, print)
        println()
        
        handler2 -= testObject2
        handler2.foreach(false, print)
        println()
        
        handler2 += testObject2
        handler2 += testObject2
        handler2.foreach(false, print)
        println("---")
        
        handler2.foreach(false, {obj => {handler2 += new TestObject(); true}})
        handler2.foreach(false, print)
        
        println("---")
        handler2.clear()
        handler2.foreach(false, print)
        
        class fakeHandleable extends Handleable{}
        
        println("---")
        assert(handler2.unsureAdd(new TestObject()))
        assert(!handler2.unsureAdd(new fakeHandleable()))
        handler2.foreach(false, print)
        
        assert(handler.handlingState)
        handler.handlingState = false
        assert(!handler.handlingState)
        
        println("Complete")
    }
    
    def print(o: Any) = {println(o); true}
    def printFirst(o: Any) = {println(o); false}
    
    def valueIsOfClass[ValueType, ClassType: ClassTag](value: ValueType, classTag: ClassType) = 
        value match
        {
        case _: ClassType => true
        case _ => false
        }
    
    def test2(a: Any, b: Any) = {
      val B = ClassTag(b.getClass)
      ClassTag(a.getClass) match {
        case B => "a is the same class as b"
        case _ => "a is not the same class as b"
      }  
    }
    
    def isString(a: Any) = 
    {
        val B = ClassTag(classOf[String])
        ClassTag(a.getClass()) match
        {
            case B => "a is string"
            case _ => "a is not string"
        }
    }
}