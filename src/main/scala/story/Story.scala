package story

import executor.ExecuteContext
import story.StepResultStatus.StepResultStatus

case class Story(steps: Seq[Step])

trait Step {
  def doStep(executeContext: ExecuteContext): StepResult
}

trait DoStep extends Step

trait AssertStep extends Step

case class StepGoto(param: StepParameter) extends DoStep {
  def doStep(executeContext: ExecuteContext): StepResult = {
    StepResult.Pass
  }
}

case class StepClick(param: StepParameter) extends DoStep {
  def doStep(executeContext: ExecuteContext): StepResult = {
    StepResult.Pass
  }
}

case class StepType(param: StepParameter) extends DoStep {
  def doStep(executeContext: ExecuteContext): StepResult = {
    StepResult.Pass
  }
}

case class StepAssert(param: StepParameter) extends AssertStep {
  def doStep(executeContext: ExecuteContext): StepResult = {
    StepResult.Pass
  }
}

case class StepNoop(param: StepParameter) extends DoStep {
  def doStep(executeContext: ExecuteContext): StepResult = {
    StepResult.Pass
  }
}

class UrsaParser {
  def stepMatcher(string: String): Step = {
    val regexGoto = """(Go to).*([^\s]+)""".r
    val regexClick = """(Click).*([^\s]+)""".r
    val regexType = """(Type).*([^\s]+)""".r
    val regexAssert = """(Assert).*([^\s]+)""".r

    string match {
      case regexGoto(_*) => StepGoto(StepParameter("", "", ""))
      case regexClick(_*) => StepClick(StepParameter())
      case regexType(_*) => StepType(StepParameter())
      case regexAssert(_*) => StepAssert(StepParameter())
      case _ => StepNoop(new StepParameter)
    }
  }

  def parseStory(str: String) = Story(str.split("\n")
    .map(_.trim)
    .filter(s => !s.isEmpty)
    .map(s => stepMatcher(s)))

}

case class StepParameter(id: String = ""
                         , url: String = ""
                         , expect: String = "")

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