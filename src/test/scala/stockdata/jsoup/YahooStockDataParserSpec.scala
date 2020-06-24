package stockdata.jsoup

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.jsoup.Jsoup

import scala.io.Source
import scala.util._

class YahooStockDataParserSpec extends AnyFlatSpec with Matchers {
	"The stock data parser" should "find a valid stock price for AAPL" in {
		val doc = Jsoup.parse(Source.fromURL(getClass.getResource("/TestYahooPages/aapl.html")).getLines.mkString)
		val parser = YahooStockDataParser
			.loadFromDocument(doc)
			.get
		parser.getCurrPrice shouldBe "360.48"
		parser.getDaysRange shouldBe "358.52 - 368.77"
	}

	"The stock data parser" should "not return an instance of YahooStockDataParser if invalid stock" in {
		val doc = Jsoup.parse(Source.fromURL(getClass.getResource("/TestYahooPages/dne.html")).getLines.mkString)
		val parser = YahooStockDataParser
			.loadFromDocument(doc)
		assertThrows[Exception] {
			parser.get
		}
	}
}