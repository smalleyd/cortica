package cortica.test.service;

import java.io.*;
import java.util.*;
import java.util.concurrent.Future;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.client.RestTemplate;

import cortica.test.value.NewsItem;

/** Performs RESTful call to Google News.
 *  
 *  NOTE: implemented without an interface for test.
 *  
 * @author David Small
 * @version 1.0.1
 * @since 9/25/2014
 *
 */

@Service
public class GoogleNewsSearch
{
	private static final String CALL_URL = "https://news.google.com/news/feeds?hl=en&gl=us&um=1&ie=UTF-8&output=rss&q={query}";
	@Resource(name="xmlRestTemplate") RestTemplate template;	// RestTemplate is thread safe. DLS on 9/26/2014.

	/** Call Google News and retrieve data. */
	@Async
	public Future<List<NewsItem>> retrieve(String query) throws IOException
	{
		return new AsyncResult<List<NewsItem>>(transform(makeCall(query)));
	}

	/** Makes the RESTful call and retrieves the data. */
	@SuppressWarnings("unchecked")
	public Map<String, Object> makeCall(String query) throws IOException
	{
		return template.getForObject(CALL_URL, Map.class, query);
	}

	/** Transforms the raw data into the Cortica results format. */
	@SuppressWarnings("unchecked")
	public List<NewsItem> transform(Map<String, Object> values)
	{
		Map<String, Object> channel = (Map<String, Object>) values.get("channel");
		if (null == channel)
			return Collections.EMPTY_LIST;

		// Based on the raw payload this should be a list but the default XML converter
		// only retrieves a single "item". Would need to implement a custom XMLMapper.
		Map<String, Object> item = (Map<String, Object>) channel.get("item");
		if (null == item)
			return Collections.EMPTY_LIST;

		return Arrays.asList(new NewsItem((String) item.get("title"),
		                                  (String) item.get("link"),
		                                  (String) item.get("pubDate")));
	}
}
