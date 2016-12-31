package executor

import org.scalatest._
import story.{Step, StepResult, Story}

import scala.collection.mutable.Stack

class ExecutorSpec extends FlatSpec with Matchers {
  val executor = new Executor()

  it should "Create a result" in {
    val story = new Story(List(new Step() {
      override def doStep(executeContext: ExecuteContext): StepResult = return new StepResult {}
    }))

    val result = executor.execute("runId", story)
    assert( result.runId == "runId")
  }
}