package story

import com.typesafe.scalalogging.LazyLogging
import db.SchemaMigration
import org.scalatest._
import executor.{ExecuteContext, Executor, StepExecuteResult}

/**
  * Created by mahasak on 12/31/2016 AD.
  */
class BearStoryParserSpec extends FlatSpec with Matchers{
  val executor = new Executor()

  it should "parse a story from string" in {
    val parser = new UrsaParser
    val storyTest =
      """
        |Go to "www.agoda.com"
        |Assert title should be "Agoda"
      """.stripMargin

    println(parser.parseStory(storyTest))
  }

  it should "open and close web browser" in {
    val executeContext = new ExecuteContext("test-001")
    val gotoStep = new StepGoto(new StepParameter("","http://www.agoda.com",""))
    val closeStep = new StepClose(new StepParameter())

    val resultGoto = gotoStep.doStep(executeContext)
    val resultClose = closeStep.doStep(executeContext)

    assert(resultGoto.status == StepResultStatus.Pass)
    assert(resultClose.status == StepResultStatus.Pass)
  }

  it should "open and close web browser from story" in {
    val parser = new UrsaParser
    val storyTest =
      """
        |Go to "www.agoda.com"
      """.stripMargin

    val story = parser.parseStory(storyTest)
    println(story)
    val result = executor.execute("test-002", story)

  }


}
