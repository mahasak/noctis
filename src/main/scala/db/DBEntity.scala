package db

import config.Config
import scalikejdbc._
import story.{StepResult, StepResultStatus}

trait DBEntity {
  Class.forName(Config.get("jdbc.main.class"))
  ConnectionPool.singleton(Config.get("jdbc.main.url"), "", "")

  implicit val session = AutoSession

  def insert()(implicit s: DBSession = AutoSession):Long
}

case class StepExecuteResult(
  val id: Option[Long],
  val runId: String,
  val startTime:Long,
  val endTime: Long,
  val status: String,
  val ex:String
) extends DBEntity {
  override def insert()(implicit s: DBSession = AutoSession): Long = {
    sql"insert into StepExecuteResult(runId, startTime, endTime, status, ex) values (${runId}, ${startTime}, ${endTime}, ${status}, ${ex})"
      .updateAndReturnGeneratedKey.apply()
  }
}