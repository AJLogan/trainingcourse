package yahoo;

import java.util.Map;

// Get quote data from Yahoo, usage java TestYahoo <stock1> <stock2> ....

public class TestYahoo {
	public static void main(String[] args) throws Exception {
		GetYahooMarketData yahooReader = new GetYahooMarketData();
		String[] keys = {"AAPL", "GOOGL", "FB"};
		while (true) {
			// Get Quotes
			Map<String, GetYahooMarketData.QuoteData> data = yahooReader
					.getQuote(keys);
			for (Map.Entry<String, GetYahooMarketData.QuoteData> entry : data
					.entrySet()) {
				GetYahooMarketData.QuoteData quote = entry.getValue();
				System.out.printf("%s [%dx%.2f] x [%.2fx%d]\n", entry.getKey(),
						quote.bidSize, quote.bidPrice, quote.askPrice,
						quote.askSize);
			}
		}
	}
}
	