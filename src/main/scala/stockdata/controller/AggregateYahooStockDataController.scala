package stockdata.controller

import scala.concurrent.{Future, Await}
import scala.concurrent.duration._

class AggregateYahooStockDataController(controllers: Array[YahooStockData]) {

	/**
	 * Gets array of loaded YahooStockData controllers
	 * @return array of controllers
	 */
	def getControllers: Array[YahooStockData] = controllers
}

object AggregateYahooStockDataController {

	/**
	 * The execution context for the app
	 * @type scala.concurrent.ExecutionContext
	 */
	implicit val ec: scala.concurrent.ExecutionContext = scala.concurrent.ExecutionContext.global

	/**
	 * Loads controllers in parallel to speed up I/O processes
	 * @param tickers The list of tickers to load YahooStockData controllers
	 *                with in parallel
	 * @return AggregateYahooStockDataController
	 */
	def loadFromTickers(tickers: Array[String]): AggregateYahooStockDataController = {
		val controllers = for (t <- tickers) yield Future {
			YahooStockData.loadFromTicker(t)
		}
		new AggregateYahooStockDataController(
			controllers.map(Await.result(_, Duration(10000, MILLISECONDS)))
		)
	}
}