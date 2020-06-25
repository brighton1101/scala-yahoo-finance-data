package stockdata.model

case class ApiResponse(
	success: Boolean = false,
	data: Array[YahooStockResponse] = null
)