package executor

object Repoter {
  def report(stepExecuteResult: StepExecuteResult):Unit = {
    stepExecuteResult.insert()
  }
}
