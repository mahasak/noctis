package story

import org.scalatest.FunSuite

/**
  * Created by mahasak on 12/31/2016 AD.
  */
class BearStoryParserSpec extends FunSuite {

  test("testParseStory") {
    var parser = new BearStoryParser
    val storyTest =
      """
        |Go to "www.agoda.com"
        |Assert title should be "Agoda"
      """.stripMargin

    println(parser.parseStory(storyTest))
  }

  ignore("testStepMatcher") {
  }

}
