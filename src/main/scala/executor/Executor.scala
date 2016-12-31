package executor

import com.typesafe.scalalogging.LazyLogging
import org.openqa.selenium.firefox.FirefoxDriver
import story.Story

class Executor extends LazyLogging {
  def execute(runId: String, story: Story): Unit = {

    logger.debug("Executing story: " + story)

    val startTime = System.currentTimeMillis

    val executeContext = new ExecuteContext()
    for(step <- story.steps) {


      try {
        step.doStep(executeContext)
      } finally {

      }
    }
  }
}
class StepResult(
  var time: Option[Long]
)

class ExecuteContext {
  val driver = new FirefoxDriver();
}