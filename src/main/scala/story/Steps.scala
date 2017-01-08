package story

import executor.ExecuteContext
import org.openqa.selenium.support.ui.{ExpectedCondition, WebDriverWait}
import org.openqa.selenium.{By, Keys, WebDriver, WebElement}




case class StepGoto(param: StepParameter) extends DoStep {
  def doStep(executeContext: ExecuteContext): StepResult = {
    executeContext.driver.get(param.url)
    StepResult.Pass
  }
}

case class StepClose(param: StepParameter) extends DoStep {
  def doStep(executeContext: ExecuteContext): StepResult = {
    executeContext.driver.quit
    StepResult.Pass
  }
}

case class StepClick(param: StepParameter) extends DoStep {
  def doStep(executeContext: ExecuteContext): StepResult = {
    getElementById(executeContext.driver, param.id).click
    StepResult.Pass
  }
}

case class StepSubmit(param: StepParameter) extends DoStep {
  def doStep(executeContext: ExecuteContext): StepResult = {
    getElementById(executeContext.driver, param.id).submit
    StepResult.Pass
  }
}

case class StepClear(param: StepParameter) extends DoStep {
  def doStep(executeContext: ExecuteContext): StepResult = {
    getElementById(executeContext.driver, param.id).clear
    StepResult.Pass
  }
}

case class StepType(param: StepParameter) extends DoStep {
  def doStep(executeContext: ExecuteContext): StepResult = {
    getElementById(executeContext.driver, param.id).sendKeys(param.expect)
    StepResult.Pass
  }
}

case class StepPressEnter(param: StepParameter) extends DoStep {
  def doStep(executeContext: ExecuteContext): StepResult = {
    getElementById(executeContext.driver, param.id).sendKeys(Keys.RETURN)
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

case class StepShouldDisplayText(param: StepParameter) extends AssertStep {
  def doStep(executeContext: ExecuteContext): StepResult = {
    getElementById(executeContext.driver, param.id)
      .getAttribute("value")
      .equals(param.expect) match {
      case true => StepResult.Pass
      case _ => StepResult.Fail
    }
  }
}

case class StepShouldNotDisplayText(param: StepParameter) extends AssertStep {
  def doStep(executeContext: ExecuteContext): StepResult = {
    getElementById(executeContext.driver, param.id)
      .getAttribute("value")
      .equals(param.expect) match {
      case false => StepResult.Pass
      case _ => StepResult.Fail
    }
  }
}

case class StepShouldVisible(param: StepParameter) extends AssertStep {
  def doStep(executeContext: ExecuteContext): StepResult = {
    getElementById(executeContext.driver, param.id)
      .isDisplayed match {
      case true => StepResult.Pass
      case _ => StepResult.Fail
    }
  }
}

case class StepShouldNotVisible(param: StepParameter) extends AssertStep {
  def doStep(executeContext: ExecuteContext): StepResult = {
    getElementById(executeContext.driver, param.id)
      .isDisplayed match {
      case false => StepResult.Pass
      case _ => StepResult.Fail
    }
  }
}

case class StepNoop(param: StepParameter) extends DoStep {
  def doStep(executeContext: ExecuteContext): StepResult = {
    StepResult.Pass
  }
}



