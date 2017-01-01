package executor

import scalikejdbc._

import db.DBEntity
import story.{StepResult, StepResultStatus}

case class ExecuteResult(
  val runId: String,
  val stepExecuteResults: Seq[StepExecuteResult]
)

case class StepExecuteResult(
  val runId: String,
  val startTime:Long,
  val endTime: Long,
  val result: Option[StepResult],
  val ex:Option[Exception]) {

  def status = result.map( _.status ).getOrElse(StepResultStatus.Fail)

  def dbObject = db.StepExecuteResult(
    None, runId, startTime, endTime,
    status.toString,
    ex.map( _.toString ).getOrElse(null)
  )
}
object StepExecuteResult {
  class Builder(val runId:String) {
    private var startTime: Option[Long] = Option.empty
    private var endTime: Option[Long] = Option.empty
    private var exception: Option[Exception] = Option.empty
    private var result: Option[StepResult] = Option.empty

    def withException(e:Exception):Builder = { exception = Some(e); this; }
    def withResult(r:StepResult):Builder = { result = Some(r); this; }
    def withStartTime(t: Long):Builder = { startTime = Some(t); this; }
    def withEndTime(t: Long):Builder = { endTime = Some(t); this; }

    def build():StepExecuteResult = {
      if(startTime.isEmpty || endTime.isEmpty) {
        throw new IllegalArgumentException("No start or end time")
      }
      if(exception.isDefined && result.isDefined) {
        throw new IllegalArgumentException("Both exception and result is defined")
      }

      return StepExecuteResult(runId, startTime.get, endTime.get, result, exception)
    }
  }
}

