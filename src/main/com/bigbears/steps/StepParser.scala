import com.sun.org.apache.xerces.internal.impl.xpath.XPath


case class Story(steps: List[Step])

trait Step {
  def doThis()
}

class Parser {
  def parseStory(str: String) : Story
}



class StepGoTo extends Step
class StepAssert extends Step
class StpeType extends Step
class StepClick

class ExecuteContext

class Executor {
  def execute(story: Story): Unit = {
    val executeContext = ExecuteContext()
    for(step : story.steps)
      step.doThis(executeContext)
    }
  }
}

class ExecuteScheduler {
  def executeStrory(stories:List[Story]): Unit = {
    //Make this an actor
    for( story <- stories ) {
      val e = new Executor()
      e.execute(story)
    }
  }
}