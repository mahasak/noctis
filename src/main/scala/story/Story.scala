package story

import executor.ExecuteContext

case class Story(steps: List[Step])

trait Step {
  def doStep(executeContext: ExecuteContext)
}