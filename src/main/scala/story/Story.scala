package story

import executor.ExecuteContext

case class Story(steps: Seq[Step])

trait Step {
  def doStep(executeContext: ExecuteContext)
}

class StepGoto extends Step {
  def doStep(executeContext: ExecuteContext): Unit = {
    println("do goto step");
  }
}

class StepClick extends Step {
  def doStep(executeContext: ExecuteContext): Unit = {
    println("do click step");
  }
}

class StepType extends Step {
  def doStep(executeContext: ExecuteContext): Unit = {
    println("do type step");
  }
}

class StepAssert extends Step {
  def doStep(executeContext: ExecuteContext): Unit = {
    println("do assert step");
  }
}

class StepNoop extends Step {
  def doStep(executeContext: ExecuteContext): Unit = {
    println("do nothing step");
  }
}


class BearStoryParser {

  def stepMatcher(string: String): Step = {
    val regexGoto = """(Go to).*([^\s]+)""".r
    val regexClick = """(Click).*([^\s]+)""".r
    val regexType = """(Type).*([^\s]+)""".r
    val regexAssert = """(Assert).*([^\s]+)""".r

    string match {
      case regexGoto(_*) => new StepGoto()
      case regexClick(_*) => new StepClick()
      case regexType(_*) => new StepType()
      case regexAssert(_*) => new StepAssert()
      case _ => new StepNoop()
    }
  }

  def parseStory(str: String): Story = new Story(str.split("\n")
    .map(_.trim)
    .filter(s => !s.isEmpty)
    .map(s => stepMatcher(s)))

}
