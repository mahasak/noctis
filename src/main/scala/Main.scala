import com.typesafe.scalalogging.LazyLogging

object Main extends LazyLogging {
  def main(args: Array[String]) = {

    logger.info("Running liquibase")
    SchemaMigration.run()


  }
}