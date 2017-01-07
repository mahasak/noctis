package executor

import com.typesafe.scalalogging.LazyLogging
import config.Config
import org.openqa.selenium.firefox.FirefoxDriver
import story._

class Executor extends LazyLogging {
  def execute(runId: String, story: Story): ExecuteResult = {
    val executeContext = new ExecuteContext(runId)

    def executeSteps(steps: List[Step]):Seq[StepExecuteResult] = {
      steps match {
        case step :: remainingSteps => {
          val stepExecuteResult = executeStep(executeContext, step)
          if(step.isInstanceOf[AssertStep] || stepExecuteResult.status != StepResultStatus.Fail) {
            println(step.toString + ":" +stepExecuteResult.status.toString)
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
  private val geckoPath = System.getProperty("os.name") match {
    case "Mac OS X" => Config.get("gecko.path.osx")
    case "Linux" => Config.get("gecko.path.linux")
    case _ => Config.get("gecko.path.windows")
  }

  System.setProperty("webdriver.gecko.driver", geckoPath)
  lazy val driver = new FirefoxDriver()
}