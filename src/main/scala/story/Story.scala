package story

import executor.ExecuteContext
import org.openqa.selenium.support.ui.{ExpectedCondition, WebDriverWait}
import org.openqa.selenium.{By, Keys, WebDriver, WebElement}
import story.StepResultStatus.StepResultStatus

case class Story(steps: List[Step])

trait Step {
  def waitFor(driver: WebDriver, f: (WebDriver) => WebElement, timeOut: Int) : WebElement = {
    new WebDriverWait(driver, timeOut).until(
      new ExpectedCondition[WebElement] {
        override def apply(d: WebDriver) = f(d)
      })
  }

  def doStep(executeContext: ExecuteContext): StepResult
}

trait DoStep extends Step

trait AssertStep extends Step

case class StepGoto(param: StepParameter) extends DoStep {
  def doStep(executeContext: ExecuteContext): StepResult = {
    executeContext.driver.get(param.url)
    StepResult.Pass
  }
}

case class StepClose(param: StepParameter) extends DoStep {
  def doStep(executeContext: ExecuteContext): StepResult = {
    executeContext.driver.quit()
    StepResult.Pass
  }
}

case class StepClick(param: StepParameter) extends DoStep {
  def doStep(executeContext: ExecuteContext): StepResult = {
    waitFor(executeContext.driver, _.findElement(By.id(param.id)), 5)
    executeContext.driver.findElement(By.id(param.id)).click()
    StepResult.Pass
  }
}

case class StepType(param: StepParameter) extends DoStep {
  def doStep(executeContext: ExecuteContext): StepResult = {
    waitFor(executeContext.driver, _.findElement(By.id(param.id)), 5)
    val e = executeContext.driver.findElement(By.id(param.id))
    e.clear
    e.sendKeys(param.expect)
    StepResult.Pass
  }
}

case class StepPressEnter(param: StepParameter) extends DoStep {
  def doStep(executeContext: ExecuteContext): StepResult = {
    waitFor(executeContext.driver, _.findElement(By.id(param.id)), 5)
    executeContext.driver.findElement(By.id(param.id)).sendKeys(Keys.RETURN)
    StepResult.Pass
  }
}

case class StepWait(param: StepParameter) extends DoStep {
  def doStep(executeContext: ExecuteContext): StepResult = {
    Thread.sleep(param.expect.toInt)
    StepResult.Pass
  }
}
case class StepAssert(param: StepParameter) extends AssertStep {
  def doStep(executeContext: ExecuteContext): StepResult = {
    StepResult.Pass
  }
}

case class StepNoop(param: StepParameter) extends DoStep {
  def doStep(executeContext: ExecuteContext): StepResult = {
    StepResult.Pass
  }
}

class UrsaParser {
  def stepMatcher(string: String): Step = {
    val regexGoto = """(Go to) "(.*?)"""".r
    val regexClick = """Click on "(.*?)"""".r
    val regexType = """Type "(.*?)" in "(.*?)"""".r
    val regexPressEnter = """Press ENTER on "(.*?)"""".r
    val regexAssert = """(Assert).*([^\s]+)""".r
    val regexWaitFor = """Wait for "(.*?)" ms""".r
    val regexClose = """(Close browser)""".r

    string match {
      case regexGoto(_, url) => StepGoto(StepParameter("", url, ""))
      case regexClick(id) => StepClick(StepParameter(id,"",""))
      case regexType(text,id) => StepType(StepParameter(id, "", text))
      case regexPressEnter(id) => StepPressEnter(StepParameter(id, "", ""))
      case regexAssert(_*) => StepAssert(StepParameter())
      case regexClose(_*) => StepClose(StepParameter())
      case regexWaitFor(ms) => StepWait(StepParameter("", "", ms))
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

case class StepParameter(id: String = ""
                         , url: String = ""
                         , expect: String = "")

case class StepResult(status: StepResultStatus)

object StepResult {
  val Pass = StepResult(StepResultStatus.Pass)
  val Fail = StepResult(StepResultStatus.Fail)
}

object StepResultStatus extends Enumeration {
  type StepResultStatus = Value
  val Pass = Value(0)
  val Fail = Value(1)
}