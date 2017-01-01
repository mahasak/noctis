package config

import com.typesafe.config.ConfigFactory

object Config {
  val config = ConfigFactory.load()

  def get(path: String) = config.getString(path)
}