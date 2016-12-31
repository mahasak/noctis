package story

import executor.ExecuteContext
import story.StepResultStatus.StepResultStatus

case class Story(steps: Seq[Step])

trait Step {
  def doStep(executeContext: ExecuteContext) : StepResult
}

class StepGoto extends Step {
  def doStep(executeContext: ExecuteContext): StepResult = {
    return new StepResult
  }
}

class StepClick extends Step {
  def doStep(executeContext: ExecuteContext): StepResult = {
    return new StepResult
  }
}

class StepType extends Step {
  def doStep(executeContext: ExecuteContext): StepResult = {
    return new StepResult
  }
}

class StepAssert extends Step {
  def doStep(executeContext: ExecuteContext): StepResult = {
    return new StepResult
  }
}

class StepNoop extends Step {
  def doStep(executeContext: ExecuteContext): StepResult = {
    return new StepResult
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

case class StepResult(status: StepResultStatus)
object StepResult {
  val Pass = StepResult(StepResultStatus.Pass)
  val Fail = StepResult(StepResultStatus.Fail)
}

object StepResultStatus extends Enumeration {
  type StepResultStatus = Value
  val Pass = Value(0)
  val Fail = Value(1)
}