import com.sun.org.apache.xerces.internal.impl.xpath.XPath




class Parser {
  def parseStory(str: String) : Story
}



class StepGoTo extends Step
class StepAssert extends Step
class StpeType extends Step
class StepClick

class ExecuteContext

class ExecuteScheduler {
  def executeStrory(stories:List[Story]): Unit = {
    //Make this an actor
    for( story <- stories ) {
      val e = new Executor()
      e.execute(story)
    }
  }
}