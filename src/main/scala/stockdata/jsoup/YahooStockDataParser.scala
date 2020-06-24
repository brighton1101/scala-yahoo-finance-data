package stockdata.jsoup

import org.jsoup.nodes.Document
import stockdata.util.Config
import scala.util._

/**
 * A class for processing Jsoup documents created from
 * Yahoo stock information web pages
 * 
 * NOTE: You can easily build off of this by getting the 
 * proper selector for whatever property you would like to 
 * retrieve. Add to stocksPage.htmlTags.{your tag here} and 
 * add a method below
 * 
 * @see https://finance.yahoo.com/quote/AAPL for an example
 *      of what kind of document is being processed
 * @see YahooStockDataParserSpec to see the output format for 
 *      each available property
 *
 * @param document Jsoup document from yahoo stock page
 */
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
	 * Gets day change info from document
	 * @return string rep of day change
	 *         ie `-6.10 (-1.66%)`
	 */
	def getDayChange: String = {
		getHtmlProperty("dayChange")
	}

	/**
	 * Gets open info from document
	 * @return string rep of open ie `365.00`
	 */
	def getOpen: String = {
		getHtmlProperty("open")
	}

	/**
	 * Gets previous close from document
	 * @return previous close string
	 */
	def getPrevClose: String = {
		getHtmlProperty("prevClose")
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
	 * Gets 52 week range info from document
	 * @return range info ie `price1 - price2`
	 */
	def getYearRange: String = {
		getHtmlProperty("yearRange")
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
	 * Gets current ask info from document
	 * @return ask info string representation
	 *         ie ``362.05 x 3100`
	 */
	def getAskInfo: String = {
		getHtmlProperty("askInfo")
	}

	/**
	 * Gets current volume info from document
	 * @return volume info ie `32,972,981`
	 */
	def getVolume: String = {
		getHtmlProperty("volume")
	}

	/**
	 * Gets avg volume info from document
	 * @return avg volume info ie `32,972,981`
	 */
	def getAvgVolume: String = {
		getHtmlProperty("avgVolume")
	}

	/**
	 * Gets market cap info from document
	 * @return market cap ie `1.562T`
	 */
	def getMarketCap: String = {
		getHtmlProperty("marketCap")
	}

	/**
	 * Gets Beta (5Y Monthly) info from document
	 * @return beta ie `1.17`
	 */
	def getBeta: String = {
		getHtmlProperty("beta")
	}

	/**
	 * Gets PE Ratio (TTM) info from document
	 * @return pe ratio ie `28.32`
	 */
	def getPeRatio: String = {
		getHtmlProperty("peRatio")
	}

	/**
	 * Gets PS (TTM) info from document
	 * @return eps info ie `12.73`
	 */
	def getEps: String = {
		getHtmlProperty("eps")
	}

	/**
	 * Gets earnings date info from document
	 * @return earnings date ie `Jul 28, 2020 - Aug 03, 2020`
	 */
	def getEarningsDate: String = {
		getHtmlProperty("earningsDate")
	}

	/**
	 * Gets Forward Dividend & Yield from document
	 * @return forward dividend and yield
	 *         ie `3.28 (0.89%)`
	 */
	def getForwardDividendYield: String = {
		getHtmlProperty("forwardDividendYield")
	}

	/**
	 * Gets 1 year est from document
	 * @return 1 year est ie `332.43`
	 */
	def getYearTargetEst: String = {
		getHtmlProperty("yearTargetEst")
	}

	/**
	 * Gets ex dividend date info from document
	 * @return ex dividend date ie `May 08, 2020`
	 */
	def getExDividendDate: String = {
		getHtmlProperty("exDividendDate")
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
