package db

import config.Config
import scalikejdbc._

trait DBEntity {
  Class.forName(Config.get(Config.get("jdbc.main.class")))
  ConnectionPool.singleton(Config.get("jdbc.main.url"), "", "")

  implicit val session = AutoSession

  def insert()(implicit s: DBSession = AutoSession):Long
}