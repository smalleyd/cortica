package cortica.test.service;

import java.io.*;
import java.util.concurrent.Future;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.client.RestTemplate;

import cortica.test.value.GoogleNewsFeed;

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
	public Future<GoogleNewsFeed> makeCall(String query) throws IOException
	{
		return new AsyncResult<GoogleNewsFeed>(template.getForObject(CALL_URL, GoogleNewsFeed.class, query));
	}
}
