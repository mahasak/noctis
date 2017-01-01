package executor

import com.typesafe.scalalogging.LazyLogging
import org.openqa.selenium.firefox.FirefoxDriver
import story._

class Executor extends LazyLogging {
  def execute(runId: String, story: Story): ExecuteResult = {
    val executeContext = new ExecuteContext()

    def executeSteps(steps: Seq[Step]):Seq[StepExecuteResult] = {
      steps match {
        case step :: remainingSteps => {
          val stepExecuteResult = executeStep(executeContext, step)

          if(step.isInstanceOf[AssertStep] || stepExecuteResult.status != StepResultStatus.Fail) {
            return stepExecuteResult +: executeSteps(remainingSteps)
          } else {
            return Seq(stepExecuteResult)
          }
        }
        case _ => Seq()
      }
    }

    logger.debug("Executing story: " + story + ", with runId: " + runId)
    return new ExecuteResult( runId, executeSteps(story.steps) )
  }



  def executeStep(executeContext: ExecuteContext, step: Step): StepExecuteResult = {
    val rb = new StepExecuteResult.Builder()

    rb.withStartTime(System.currentTimeMillis())
    try {
      rb.withResult( step.doStep(executeContext) )
    } catch {
      case e:Exception => rb.withException( e )
    }
    finally {
      rb.withEndTime(System.currentTimeMillis())
    }

    return rb.build()
  }
}

case class ExecuteResult(
                          val runId: String,
                          val stepExecuteResults: Seq[StepExecuteResult]
                        )

case class StepExecuteResult(
    val startTime:Long,
    val endTime: Long,
    val result: Option[StepResult],
    val ex:Option[Exception]) {
  def status = result.map( _.status ).getOrElse(StepResultStatus.Fail)
}
object StepExecuteResult {
  class Builder {
    private var startTime: Option[Long] = Option.empty
    private var endTime: Option[Long] = Option.empty
    private var exception: Option[Exception] = Option.empty
    private var result: Option[StepResult] = Option.empty

    def withException(e:Exception) = { exception = Some(e) }
    def withResult(r:StepResult) = { result = Some(r) }
    def withStartTime(t: Long) = { startTime = Some(t) }
    def withEndTime(t: Long) = { endTime = Some(t) }

    def build():StepExecuteResult = {
      if(startTime.isEmpty || endTime.isEmpty) {
        throw new IllegalArgumentException("No start or end time")
      }
      if(exception.isDefined && result.isDefined) {
        throw new IllegalArgumentException("Both exception and result is defined")
      }

      return StepExecuteResult(startTime.get, endTime.get, result, exception)
    }
  }
}

class ExecuteContext {
  lazy val driver = new FirefoxDriver()
}