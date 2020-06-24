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

### Roadmap / What needs to be done:
- Add GCP cloudfunction wrapper to make
- Add simple cli for ease of use
- Tests for controller class