package stockdata.jsoup

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

object Parser {

	/**
	 * Gets parsed document by url
	 * @param url The url to get html from
	 * @return parsed html document
	 */
	def getParsedHtml(url: String): Document = {
		Jsoup
			.connect(url)
			.get
	}

	/**
	 * Using generic selector, get first element from that 
	 * selector and return the text within
	 * @param document The jsoup document object to search element for
	 * @return the value of the text or null if DNE
	 */
	def getTextFromElement(document: Document, selector: String): String = {
		val elements = document
			.select(selector)
		if (elements.isEmpty) null
		else {
			elements
				.first
				.text
		}
			
	}
}