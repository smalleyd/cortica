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

/** Performs RESTful call to You Tube.
 *  
 *  NOTE: implemented without an interface for test.
 *  
 * @author David Small
 * @version 1.0.1
 * @since 9/25/2014
 *
 */

@Service
public class YouTubeSearch
{
	private static final String CALL_URL = "http://gdata.youtube.com/feeds/api/videos?alt=json&q={query}";
	@Resource(name="defaultRestTemplate") RestTemplate template;	// RestTemplate is thread safe. DLS on 9/26/2014.

	/** Call You Tube and retrieve data. */
	@Async
	public Future<List<NewsItem>> retrieve(String query) throws IOException
	{
		return new AsyncResult<List<NewsItem>>(transform(makeCall(query)));
	}

	/** Make the RESTful call and convert to a list of NewsItem values. */
	@SuppressWarnings("unchecked")
	public Map<String, Object> makeCall(String query) throws IOException
	{
		return template.getForObject(CALL_URL, Map.class, query);
	}

	/** Make the RESTful call and convert to a list of NewsItem values. */
	@SuppressWarnings("unchecked")
	public List<NewsItem> transform(Map<String, Object> values) throws IOException
	{
		Map<String, Object> feed = (Map<String, Object>) values.get("feed");
		if (null == feed)
			return Collections.EMPTY_LIST;

		List<Map<String, Object>> entries = (List<Map<String, Object>>) feed.get("entry");
		if ((null == entries) || entries.isEmpty())
			return Collections.EMPTY_LIST;

		List<NewsItem> results = new ArrayList<>(entries.size());
		for (Map<String, Object> entry : entries)
			results.add(new NewsItem(getSubElement(entry, "title", "$t"),
			                         getFirstSubElement(entry, "link", "href"),
			                         getSubElement(entry, "published", "$t"),
			                         getFirstSubElement(entry, "author", "name")));

		return results;
	}

	/** Helper method - gets the sub element if available. */
	@SuppressWarnings("unchecked")
	private String getSubElement(Map<String, Object> entry, String name, String subName)
	{
		Map<String, Object> subElement = (Map<String, Object>) entry.get(name);
		if (null == subElement)
			return null;

		return (String) subElement.get(subName);
	}

	/** Helper method - gets the first subelement in the list. */
	@SuppressWarnings("unchecked")
	private String getFirstSubElement(Map<String, Object> entry, String name, String subName)
	{
		List<Map<String, Object>> subElements = (List<Map<String, Object>>) entry.get(name);
		if ((null == subElements) || subElements.isEmpty())
			return null;

		Object element = subElements.get(0).get(subName);
		if (null == element)
			return null;

		if (element instanceof String)
			return (String) element;

		return (String) ((Map<String, Object>) element).get("$t");
	}
}
