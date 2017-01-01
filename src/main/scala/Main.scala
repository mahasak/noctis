import com.typesafe.scalalogging.LazyLogging
import db.SchemaMigration
import story.UrsaParser

object Main extends LazyLogging {
  def main(args: Array[String]):Unit = {

    logger.info("Running liquibase")

    SchemaMigration.run()

    val parser = new UrsaParser
    val storyTest =
      """
        |Go to "www.agoda.com"
        |Assert title should be "Agoda"
      """.stripMargin

    parser.parseStory(storyTest)
  }
}
