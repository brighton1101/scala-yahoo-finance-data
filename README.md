# Yahoo Stock Client Written in Scala

Unfortunately, Yahoo does not have a publicly available API for 
retrieving stock information anymore. However, they still have
stock information live on their site that can be parsed.

### Requirements:
- Scala
- SBT
- Java 8

### What this does:
- Client with controller class to retrieve stock data from Yahoo Stocks
- Accepts ticker as a parameter and returns object containing all relevant fields available online

### Usage via Scala shell
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

### Roadmap / What needs to be done:
- Add GCP cloudfunction wrapper to make
- Tests for controller class