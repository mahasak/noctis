package executor

import java.util.UUID

import common.DatabaseEnabled
import org.scalatest._

class ReporterSpec extends FunSuite with Matchers with DatabaseEnabled {
  test("Insert step execute result") {
    val runId = UUID.randomUUID().toString
    val stepExecuteResult = new StepExecuteResult.Builder(runId)
        .withStartTime(System.currentTimeMillis())
        .withEndTime(System.currentTimeMillis())
        .build()
    stepExecuteResult.insert()
  }
}
