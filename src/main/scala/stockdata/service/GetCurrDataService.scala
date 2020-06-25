package stockdata.service

import stockdata.controller.AggregateYahooStockDataController
import stockdata.model.{ApiResponse}
import com.google.cloud.functions.{HttpFunction, HttpRequest, HttpResponse}
import com.google.gson.Gson;

class GetCurrDataService extends HttpFunction {

  /**
    * Gson instance for service
    * @type Gson
    */
  val gson: Gson = new Gson

  /**
    * Passthrough method for Google Cloudfunctions. Allows users to
    * hit the endpoint with the query param ticker set to a comma
    * separated list of tickers, in the format AAPL,ETSY,etc.
    */
  override def service(
      httpRequest: HttpRequest,
      httpResponse: HttpResponse
  ): Unit = {
    val tickers: Array[String] =
      httpRequest
        .getFirstQueryParameter("ticker")
        .orElse("")
        .split(",")
    val res = getResponseFromTickers(tickers)
    if (!res.success) httpResponse.setStatusCode(400)
    httpResponse.getWriter.write(
      gson.toJson(
        res
      )
    )
    ()
  }

  /**
    * Aggregates response from tickers provided. For each ticker,
    * return the response from the controller. This is useful bc
    * cloudfunctions charge per invocation. In the future, look into
    * making these calls async
    * @param tickers an array of tickers to try for
    * @return ApiResponse object
    */
  def getResponseFromTickers(tickers: Array[String]): ApiResponse = {
    if (tickers.isEmpty || tickers(0) == "") return new ApiResponse
    else {
      try {
        val agController = AggregateYahooStockDataController
          .loadFromTickers(tickers)
        val stockResponses = agController.getControllers
          .map(c => c.getResponse)
        ApiResponse(
          true,
          stockResponses
        )
      } catch {
        case e: Throwable => new ApiResponse
      }
    }
  }
}
