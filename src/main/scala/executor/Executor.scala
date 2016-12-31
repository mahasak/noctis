package executor

import com.typesafe.scalalogging.LazyLogging
import org.openqa.selenium.firefox.FirefoxDriver
import story.{Step, Story}

class Executor extends LazyLogging {
  def execute(runId: String, story: Story): ExecuteResult = {

    logger.debug("Executing story: " + story + ", with runId: " + runId)

    val executeContext = new ExecuteContext()

    val stepExecuteResults =  story.steps.map( step => executeStep(executeContext, step) )

    return new ExecuteResult(runId, stepExecuteResults)
  }

  def executeStep(executeContext: ExecuteContext, step: Step): StepExecuteResult = {
    val startTime = System.currentTimeMillis
    val stepExecuteResult = new StepExecuteResult()

    try {
      step.doStep(executeContext)
    } catch {
      case e:Exception => stepExecuteResult.exception = Some(e)
    }
    finally {
      stepExecuteResult.time = Some(System.currentTimeMillis() - startTime)
    }

    return stepExecuteResult
  }
}

case class ExecuteResult(
  val runId: String,
  val stepExecuteResults: List[StepExecuteResult]
)

class StepExecuteResult {
  var time: Option[Long] = Option.empty
  var exception: Option[Exception] = Option.empty
  var result: Option[Exception] = Option.empty
}

class ExecuteContext {
  val driver = new FirefoxDriver();
}