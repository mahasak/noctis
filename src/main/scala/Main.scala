import com.typesafe.scalalogging.LazyLogging
import story.UrsaParser

object Main extends LazyLogging {
  def main(args: Array[String]) = {

    logger.info("Running liquibase")

    SchemaMigration.run()
    var parser = new UrsaParser
    val storyTest =
      """
        |Go to "www.agoda.com"
        |Assert title should be "Agoda"
      """.stripMargin

    parser.parseStory(storyTest)
  }
}
