package executor

import com.typesafe.scalalogging.LazyLogging
import config.Config
import org.openqa.selenium.firefox.FirefoxDriver
import story._

class Executor extends LazyLogging {
  def execute(runId: String, story: Story): ExecuteResult = {
    val executeContext = new ExecuteContext(runId)

    def executeSteps(steps: Seq[Step]):Seq[StepExecuteResult] = {
      steps match {
        case step :: remainingSteps => {
          val stepExecuteResult = executeStep(executeContext, step)
          println(step.toString)
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
    val rb = new StepExecuteResult.Builder(executeContext.runId)

    rb.withStartTime(System.currentTimeMillis())
    try {
      println(step.toString)
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

class ExecuteContext(val runId:String) {
  System.setProperty("webdriver.gecko.driver", Config.get("gecko.path"))
  lazy val driver = new FirefoxDriver()
}