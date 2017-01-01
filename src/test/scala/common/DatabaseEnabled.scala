package common

import com.typesafe.scalalogging.LazyLogging
import db.SchemaMigration

trait DatabaseEnabled extends LazyLogging {
  logger.info("Running liquibase")
  SchemaMigration.run()
}
