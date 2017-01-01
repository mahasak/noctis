package executor

object Reporter {
  def report(stepExecuteResult: StepExecuteResult):Unit = {
    stepExecuteResult.dbObject.insert()
  }
}
