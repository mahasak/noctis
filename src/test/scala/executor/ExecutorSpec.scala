package executor

import org.scalatest._
import story.{Step, StepResult, Story}

import scala.collection.mutable.Stack

class ExecutorSpec extends FlatSpec with Matchers {
  val executor = new Executor()

  it should "Create a result" in {
    val stepResult = new StepResult {};

    val story = new Story(List(new Step() {
      override def doStep(executeContext: ExecuteContext): StepResult = stepResult
    }))

    val result = executor.execute("runId", story)

    assert( result.runId == "runId")
    assert( result.stepExecuteResults(0).result.get == stepResult )
  }


  it should "Record an exception" in {
    val story = new Story(List(new Step() {
      override def doStep(executeContext: ExecuteContext): StepResult = throw new IllegalArgumentException("my exception")
    }))

    val result = executor.execute("runId", story)

    assert( result.runId == "runId")
    assert( result.stepExecuteResults(0).result.isEmpty )
    assert( result.stepExecuteResults(0).ex.get.getMessage == "my exception" )
  }
}