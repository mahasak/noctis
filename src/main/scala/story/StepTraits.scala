package story

import executor.ExecuteContext
import org.openqa.selenium.{By, WebDriver, WebElement}
import org.openqa.selenium.support.ui.{ExpectedCondition, WebDriverWait}

/**
  * Created by mahasak on 1/8/2017 AD.
  */
trait Step {
  def waitFor(driver: WebDriver, f: (WebDriver) => WebElement, timeOut: Int) : WebElement = {
    new WebDriverWait(driver, timeOut).until(
      new ExpectedCondition[WebElement] {
        override def apply(d: WebDriver) = f(d)
      })
  }

  def getElementById(driver: WebDriver, id: String) : WebElement = {
    waitFor(driver, _.findElement(By.id(id)), 5)
    return driver.findElement(By.id(id))
  }

  def doStep(executeContext: ExecuteContext): StepResult
}

trait DoStep extends Step

trait AssertStep extends Step