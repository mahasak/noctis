package executor

import org.scalatest._
import story.{Step, StepResult, Story}

class ExecutorSpec extends FlatSpec with Matchers {
  val executor = new Executor()

  it should "create a result" in {
    val stepResult = StepResult.Pass

    val story = new Story(List(new Step() {
      override def doStep(executeContext: ExecuteContext): StepResult = stepResult
    }))

    val result = executor.execute("runId", story)

    assert( result.runId == "runId")
    assert( result.stepExecuteResults(0).result.get == stepResult )
  }


  it should "record an exception" in {
    val story = new Story(List(new Step() {
      override def doStep(executeContext: ExecuteContext): StepResult = throw new IllegalArgumentException("my exception")
    }))

    val result = executor.execute("runId", story)

    assert( result.runId == "runId")
    assert( result.stepExecuteResults(0).result.isEmpty )
    assert( result.stepExecuteResults(0).ex.get.getMessage == "my exception" )
  }
}