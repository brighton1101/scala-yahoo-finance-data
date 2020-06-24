package stockdata.model

case class YahooStockResponse(
	currPrice: String = null,
	dayChange: String = null,
	open: String = null,
	prevClose: String = null,
	peRatio: String = null,
	beta: String = null,
	marketCap: String = null,
	yearRange: String = null,
	avgVolume: String = null,
	volume: String = null,
	askInfo: String = null,
	bidInfo: String = null,
	daysRange: String = null,
	eps: String = null,
	earningsDate: String = null,
	forwardDividendYield: String = null,
	yearTargetEst: String = null,
	exDividendDate: String = null
)