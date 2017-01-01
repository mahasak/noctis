package story

import org.scalatest.FunSuite
import executor.{ExecuteContext, Executor, StepExecuteResult}

/**
  * Created by mahasak on 12/31/2016 AD.
  */
class BearStoryParserSpec extends FunSuite {

  test("testParseStory") {
    val parser = new UrsaParser
    val storyTest =
      """
        |Go to "www.agoda.com"
        |Assert title should be "Agoda"
      """.stripMargin

    println(parser.parseStory(storyTest))
  }

  test("gotoStepShouldOpenUrl") {
    val executeContext = new ExecuteContext("test-001")
    val gotoStep = new StepGoto(new StepParameter("","http://www.agoda.com",""))
    val result = gotoStep.doStep(executeContext)

    assert(result.status == StepResultStatus.Pass)

  }

  ignore("testStepMatcher") {
  }

}
