import org.scalatest.{FlatSpec, Matchers}
import org.testng.annotations.Test
import com.bigbears.story

import scala.collection.mutable.Stack;

/**
  *
  * @author nuboat
  */
class SearchStory extends SeleniumStory {

  @Override
  public String getStory() {
    return "search.story";
  }

  @Test
  public void test() throws Throwable {
    run();
  }

}
