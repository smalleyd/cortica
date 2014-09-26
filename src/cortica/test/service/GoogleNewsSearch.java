package cortica.test.service;

import java.io.*;
import java.util.*;
import java.util.concurrent.Future;

import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
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

	/** Call Google News and retrieve data. */
	@Async
	public Future<GoogleNewsFeed> makeCall(String query) throws IOException
	{
		// Unfortunately, MappingJackson2XmlHttpMessageConverter is not one of the default converters included in the RestTemplate. DLS on 9/26/2014.
		List<org.springframework.http.converter.HttpMessageConverter<?>> converters = new ArrayList<>(1);
		converters.add(new MappingJackson2XmlHttpMessageConverter());

		return new AsyncResult<GoogleNewsFeed>(new RestTemplate(converters).getForObject(CALL_URL, GoogleNewsFeed.class, query));
	}
}
