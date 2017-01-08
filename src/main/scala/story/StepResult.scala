package story

import story.StepResultStatus.StepResultStatus
/**
  * Created by mahasak on 1/8/2017 AD.
  */
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