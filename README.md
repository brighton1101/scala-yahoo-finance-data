# Yahoo Stock Client Written in Scala

Unfortunately, Yahoo does not have a publicly available API for 
retrieving stock information anymore. However, they still have
stock information live on their site that can be parsed.

### Requirements:
- Scala
- SBT
- Java 8
- mvn (for starting local service)
- gcloud (Google Cloud CLI) for deployment

### What this does:
- Client with controller class to retrieve stock data from Yahoo Stocks
- Accepts ticker as a parameter and returns object containing all relevant fields available online

### Usage via Scala shell
- Data on one stock
```
🌴🌴🌴 stockdata (master) $ sbt console
...
Welcome to Scala 2.13.2 (OpenJDK 64-Bit Server VM, Java 13.0.2).
Type in expressions for evaluation. Or try :help.

scala> import stockdata.controller.YahooStockData
import stockdata.controller.YahooStockData

scala> val controller = YahooStockData.loadFromTicker("AAPL")
val controller: stockdata.controller.YahooStockData = stockdata.controller.YahooStockData@48df36b0

scala> val response = controller.getResponse
val response: stockdata.model.YahooStockResponse = YahooStockResponse(360.06,-6.47 (-1.77%),365.00,366.53,28.29,1.17,1.561T,192.58 - 372.38,38,748,343,47,402,735,360.34 x 800,360.18 x 1200,358.52 - 368.77,12.73,Jul 28, 2020,3.28 (0.89%),332.43,May 08, 2020)

scala> response.currPrice
val res0: String = 360.06
```
- Data on multiple
```
🌴🌴🌴 stockdata (master) $ sbt console
...
Welcome to Scala 2.13.2 (OpenJDK 64-Bit Server VM, Java 13.0.2).
Type in expressions for evaluation. Or try :help.

scala> import stockdata.controller.AggregateYahooStockDataController
import stockdata.controller.AggregateYahooStockDataController

scala> val tickers = Array("AAPL", "ETSY")
val tickers: Array[String] = Array(AAPL, ETSY)

scala> val agController = AggregateYahooStockDataController.loadFromTickers(tickers)
val agController: stockdata.controller.AggregateYahooStockDataController = stockdata.controller.AggregateYahooStockDataController@76abfc3

scala> agController.getControllers.foreach(c => println(c.getResponse))
YahooStockResponse(AAPL,364.84,+4.78 (+1.33%),360.70,360.06,28.66,1.17,1.581T,192.58 - 372.38,38,379,645,33,707,769,364.91 x 1000,364.40 x 900,357.57 - 365.00,12.73,Jul 28, 2020,3.28 (0.91%),332.43,May 08, 2020)
YahooStockResponse(ETSY,101.28,+3.11 (+3.17%),98.30,98.17,164.68,1.65,12.02B,29.95 - 103.94,4,074,179,3,685,269,101.49 x 800,100.36 x 800,96.57 - 102.39,0.62,Jul 30, 2020,N/A (N/A),null,null)
```

### Usage via GCP Cloudfunctions
- Unfortunately, only POM files are supported here
- The deployment config (see below) copies a POM file out of the `/cloudfunctions` dir that contains all dependencies and puts it in the root
- TO USE SERVICE LOCALLY: from the root do the following
```
chmod u+x start-local-service.sh
./start-local-service.sh
```

### Usage via API Service
- By default locally the service is started on port 8080, and only has one available endpoint
- Add a query param for ticker, followed by list of tickers that you would like data for separated by commas
- ie `ticker=AAPL,ETSY`
- Example call:
```
🌴🌴🌴 ~ $ curl http://localhost:8080?ticker=etsy,aapl
{"success":true,"data":[{"currPrice":"100.79","dayChange":"+2.62 (+2.67%)","open":"98.30","prevClose":"98.17","peRatio":"163.89","beta":"1.65","marketCap":"11.962B","yearRange":"29.95 - 103.94","avgVolume":"4,074,179","volume":"2,728,085","askInfo":"100.54 x 800","bidInfo":"100.39 x 800","daysRange":"96.57 - 102.39","eps":"0.62","earningsDate":"Jul 30, 2020","forwardDividendYield":"N/A (N/A)"},{"currPrice":"362.11","dayChange":"+2.05 (+0.57%)","open":"360.70","prevClose":"360.06","peRatio":"28.45","beta":"1.17","marketCap":"1.569T","yearRange":"192.58 - 372.38","avgVolume":"38,379,645","volume":"27,011,411","askInfo":"360.95 x 1400","bidInfo":"360.90 x 900","daysRange":"357.57 - 364.02","eps":"12.73","earningsDate":"Jul 28, 2020","forwardDividendYield":"3.28 (0.91%)","yearTargetEst":"332.43","exDividendDate":"May 08, 2020"}]}
```

### Roadmap / What needs to be done:
- Tests for controller class
- Background service triggered at end of day to archive stock prices of popular stocks (S&P 500?) and record results to GCS

### Deployment / Configuration Strategy for Endpoint
- Uses Google Cloud / Cloudbuilds to deploy (essentially breaks build steps down into operations within Docker containers)
- Ensure all necessary permissions are set (enable Google Cloudfunctions API and Cloudbuilds API)
- Run `gcloud builds submit`
- If you get an access denied for GCS, give the cloudfunction service agent access to GCS in console
