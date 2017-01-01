package executor

import org.scalatest._
import story._

class ExecutorSpec extends FlatSpec with Matchers {
  val executor = new Executor()

  private def createStep(stepResult: StepResult) = new DoStep() {
    override def doStep(executeContext: ExecuteContext): StepResult = stepResult
  }


  private def createAssertStep(stepResult: StepResult) = new AssertStep() {
    override def doStep(executeContext: ExecuteContext): StepResult = stepResult
  }

  it should "create a result" in {
    val stepResult = StepResult.Pass

    val story = new Story(List(createStep(stepResult)))

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

  it should "execute until fail" in {
    val story = new Story(List(
      createStep(StepResult.Pass),
      createStep(StepResult.Fail),
      createStep(StepResult.Pass)
    ))

    val result = executor.execute("runId", story)

    assert( result.stepExecuteResults.size == 2 )
  }

  it should "execute Ignore fail if Assert type" in {
    val story = new Story(List(
      createAssertStep(StepResult.Pass),
      createAssertStep(StepResult.Fail),
      createAssertStep(StepResult.Pass)
    ))

    val result = executor.execute("runId", story)

    assert( result.stepExecuteResults.size == 3 )
  }


}