package executor

import com.typesafe.scalalogging.LazyLogging
import story.Story

class Executor extends LazyLogging {
  def execute(runId: String, story: Story): Unit = {

    logger.debug("Executing story: " + story)

    val executeContext = new ExecuteContext()
    for(step <- story.steps)
      step.doStep(executeContext)
  }
}


class ExecuteContext {

}