package stockdata.jsoup

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.jsoup.Jsoup

import scala.io.Source
import scala.util._

class YahooStockDataParserSpec extends AnyFlatSpec with Matchers {
	"The stock data parser" should "find a valid stock price for AAPL" in {
		val doc = Jsoup.parse(Source.fromURL(getClass.getResource("/TestYahooPages/aapl")).getLines.mkString)
		val parser = YahooStockDataParser
			.loadFromDocument(doc)
			.get
		parser.getPeRatio shouldBe "28.32"
		parser.getBeta shouldBe "1.17"
		parser.getMarketCap shouldBe "1.562T"
		parser.getPrevClose shouldBe "366.53"
		parser.getYearRange shouldBe "192.58 - 372.38"
		parser.getAvgVolume shouldBe "38,749,604"
		parser.getVolume shouldBe "32,972,981"
		parser.getAskInfo shouldBe "362.05 x 3100"
		parser.getBidInfo shouldBe "362.01 x 800"
		parser.getCurrPrice shouldBe "360.48"
		parser.getDaysRange shouldBe "358.52 - 368.77"
		parser.getDayChange shouldBe "-6.05 (-1.65%)"
		parser.getOpen shouldBe "365.00"
		parser.getEps shouldBe "12.73"
		parser.getEarningsDate shouldBe "Jul 28, 2020"
		parser.getForwardDividendYield shouldBe "3.28 (0.89%)"
		parser.getYearTargetEst shouldBe "332.43"
		parser.getExDividendDate shouldBe "May 08, 2020"
	}

	"The stock data parser" should "not return an instance of YahooStockDataParser if invalid stock" in {
		val doc = Jsoup.parse(Source.fromURL(getClass.getResource("/TestYahooPages/dne")).getLines.mkString)
		val parser = YahooStockDataParser
			.loadFromDocument(doc)
		assertThrows[Exception] {
			parser.get
		}
	}
}