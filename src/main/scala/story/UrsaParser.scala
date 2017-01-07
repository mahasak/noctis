package story

/**
  * Created by mahasak on 1/7/2017 AD.
  */

class UrsaParser {
  def stepMatcher(string: String): Step = {
    val regexGoto = """(Go to) "(.*?)"""".r
    val regexClick = """Click on "(.*?)"""".r
    val regexType = """Type "(.*?)" in "(.*?)"""".r
    val regexClear = """Clear "(.*?)"""".r
    val regexPressEnter = """Press ENTER on "(.*?)"""".r
    val regexAssert = """(Assert).*([^\s]+)""".r
    val regexDisplay = """"(.*?)" should (.*?) display "(.*?)"""".r
    val regexVisible = """"(.*?)" should (.*?) visible""".r
    val regexWaitFor = """Wait for "(.*?)" ms""".r
    val regexSubmit = """Submit "(.*?)"""".r
    val regexClose = """(Close browser)""".r

    string match {
      case regexGoto(_, url) => StepGoto(StepParameter("", url, ""))
      case regexClick(id) => StepClick(StepParameter(id,"",""))
      case regexType(text,id) => StepType(StepParameter(id, "", text))
      case regexClear(id) => StepClear(StepParameter(id, "", ""))
      case regexPressEnter(id) => StepPressEnter(StepParameter(id, "", ""))
      case regexAssert(_*) => StepAssert(StepParameter())
      case regexDisplay(id,condition,text) => displayAssertMatcher(condition,id,text)
      case regexVisible(id,condition) => visibilityAssertMatcher(condition, id)
      case regexClose(_*) => StepClose(StepParameter())
      case regexWaitFor(ms) => StepWait(StepParameter("", "", ms))
      case regexSubmit(id) => StepSubmit(StepParameter(id,"",""))
      case _ => StepNoop(new StepParameter)
    }
  }

  def displayAssertMatcher(condition: String, id:String, text: String): Step = {
    condition match {
      case "be" => StepShouldDisplayText(StepParameter(id, "", text))
      case "not" => StepShouldNotDisplayText(StepParameter(id, "", text))
      case _ => StepNoop(new StepParameter)
    }
  }

  def visibilityAssertMatcher(condition: String, id:String): Step = {
    condition match {
      case "be" => StepShouldVisible(StepParameter(id))
      case "not" => StepShouldNotVisible(StepParameter(id))
      case _ => StepNoop(new StepParameter)
    }
  }

  def parseStory(str: String) = Story(str.split("\n")
    .map(_.trim)
    .filter(s => !s.isEmpty)
    .map(s => stepMatcher(s))
    .toList
  )

}