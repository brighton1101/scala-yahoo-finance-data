package stockdata.util

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ConfigSpec extends AnyFlatSpec with Matchers {
  "The application" should "handle existent configs gracefully" in {
    val res = Config.getString("stocksPage.baseUrl")
    res shouldBe "https://finance.yahoo.com/quote/"
  }

  "The application" should "handle non-existent configs gracefully" in {
    val res = Config.getString("nonsense.should.not.work.ever")
    res shouldBe null
  }
}
