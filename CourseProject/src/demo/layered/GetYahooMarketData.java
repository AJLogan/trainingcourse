package demo.layered;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
 
// Get quote data for a list of symbols and returns it in a Map

public class GetYahooMarketData 
{
    // Quote Data class
    public class QuoteData
    {
        public float askPrice;
        public float bidPrice;
        public int   askSize;
        public int   bidSize;
    }

    public Map<String, QuoteData> getQuote(String[] stocks) throws Exception
    {
        // Build the URL  
        StringBuilder url = 
            new StringBuilder("http://finance.yahoo.com/d/quotes.csv?s=");
        for (String s : stocks)
            url.append(s + ",");
        url.deleteCharAt(url.length()-1);
        // Properties is for bid and ask
        url.append("&f=a0a5b0b6&e=.csv");
 
        // Get the csv lines from Yahoo
        List<String> csv = getCsv(url.toString());

        // Build response
        Map<String, QuoteData> quotes = new HashMap<String, QuoteData>();
        int i = 0;
        for (String line : csv)
        {
            // Parse csv lines
            String[] fields = line.split(",", -1);

            // Should be 4 values if not discard line
            if (fields.length == 4)
            {
                QuoteData quote = new QuoteData();
                try
                {
                    quote.askPrice = Float.parseFloat(fields[0]);
                    quote.askSize = Integer.parseInt(fields[1]);
                    quote.bidPrice = Float.parseFloat(fields[2]);
                    quote.bidSize = Integer.parseInt(fields[3]);
                    // Insert quote with stock as key
                    quotes.put(stocks[i], quote);
                } 
                catch (NumberFormatException e) {}
                
            }
            i++;
        }
        return quotes;
    }

    // Returns list of csv from Yahoo
    private List<String> getCsv(String url) throws Exception
    {
        List<String> response = new ArrayList<String>();
        URL obj = new URL(url);

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // This is a GET request
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        int responseCode = con.getResponseCode();
        // If the response code is not OK then return empty line
        if (responseCode != 200)
            return response; 

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;

        while ((inputLine = in.readLine()) != null)
            response.add(inputLine);

        in.close();
        return response;
    }
}
