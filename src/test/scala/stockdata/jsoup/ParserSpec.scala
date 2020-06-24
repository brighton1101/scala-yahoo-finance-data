package stockdata.jsoup

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import org.jsoup.Jsoup

class ParserSpec extends AnyFlatSpec with Matchers {
	"The parser" should "retrieve documents from a URL" in {
		val res = Parser.getParsedHtml("https://finance.yahoo.com/quote/AAPL")
		res should not be null
	}

	val testHtml = """
	<span data-reactid='50'>Hello world</span>
	"""

	"The parser" should "retrieve elements from documents successfully" in {
		val doc = Jsoup.parse(testHtml)
		val res = Parser.getTextFromElement(doc, "span[data-reactid='50']")
		res shouldBe "Hello world"
	}

	"The parser" should "handle missing elements elegantly" in {
		val doc = Jsoup.parse(testHtml)
		val res = Parser.getTextFromElement(doc, "span[data-reactid='48']")
		res shouldBe null
	}
}