import com.typesafe.scalalogging.LazyLogging
import db.SchemaMigration
import executor.{ExecuteContext, Executor}
import story.UrsaParser

object Main extends LazyLogging {
  def main(args: Array[String]):Unit = {

    logger.info("Running liquibase")

    SchemaMigration.run()

    val parser = new UrsaParser
    val storyTest =
      """
        |Go to "http://www.google.com"
        |Type "BigBears.IO" in "lst-ib"
        |Click on "_fZl"
        |Wait for "2000" ms
        |Type "Norbor" in "lst-ib"
        |Click on "_fZl"
        |Wait for "2000" ms
        |Type "Varokas Panusuwan" in "lst-ib"
        |Click on "_fZl"
        |Wait for "2000" ms
        |Close browser
      """.stripMargin

    val executor = new Executor()
    val executeContext = new ExecuteContext("test-002")
    val story = parser.parseStory(storyTest)
    val result = executor.execute("test-002", story)
    println(result)
  }
}
