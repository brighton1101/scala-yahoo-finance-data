package stockdata.jsoup

import org.jsoup.nodes.Document
import stockdata.util.Config
import scala.util._

class YahooStockDataParser(document: Document) {
	import YahooStockDataParser._

	/**
	 * Gets current stock price from document
	 * @return stock price represented as string
	 */
	def getCurrPrice: String = {
		getHtmlProperty("currPrice")
	}

	/**
	 * Gets current days range from document
	 * @return days range represented as a string
	 *         ie `price1 - price2`
	 */
	def getDaysRange: String = {
		getHtmlProperty("daysRange")
	}

	/**
	 * Gets current bid info from document
	 * @return bid info string representation
	 *         ie `362.01 x 800`
	 */
	def getBidInfo: String = {
		getHtmlProperty("bidInfo")
	}

	/**
	 * Wrapper method to pull in html stock property from abbreviated
	 * config path (ie -> only specifiying the subproperty under stocksPage.htmlTags)
	 * @param selector The subpath tag
	 * @return the value in the document
	 */
	private def getHtmlProperty(selector: String): String = {
		Parser.getTextFromElement(
			document,
			getHtmlTagConfig(selector)
		)
	}
}

object YahooStockDataParser {

	/**
	 * Loads YahooStockDataParser from ticker if valid
	 * @param ticker The ticker of the stock
	 * @return Option[YahooStockDataParser]
	 */
	def loadFromTicker(ticker: String): Option[YahooStockDataParser]  = {
		val doc = Parser.getParsedHtml(
			getUrlForTicker(ticker)
		)
		loadFromDocument(doc)
	}

	/**
	 * Loads YahooStockDataParser from document if valid
	 * @param document The jsoup document
	 * @return Option[YahooStockDataParser]
	 */
	def loadFromDocument(document: Document): Option[YahooStockDataParser] = {
		if (checkDocumentForError(document)) None
		else Some(new YahooStockDataParser(document))
	}

	/**
	 * Checks document to ensure that a valid stock page is loaded
	 * @param document The jsoup document to check
	 * @return true if error, false otherwise
	 */
	private def checkDocumentForError(document: Document): Boolean = {
		val res = Parser.getTextFromElement(
			document,
			getHtmlTagConfig("error")
		)
		if (res == null) false
		else true
	}

	/**
	 * Helper method to pull out config for selector tags
	 * to look up value by
	 * @param tagType What you are looking for with the tag
	 *        ie, "currPrice"
	 * @return the selector tags to use, or null if DNE
	 */
	private def getHtmlTagConfig(tagType: String): String = {
		Config.getString("stocksPage.htmlTags." + tagType)
	}

	/**
	 * [ticker description]
	 * @type {[type]}
	 */
	private def getUrlForTicker(t: String): String = {
		Config.getString("stocksPage.baseUrl") + t
	}
}