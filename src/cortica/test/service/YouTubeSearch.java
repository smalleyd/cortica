package cortica.test.service;

import java.io.*;
import java.util.concurrent.Future;

import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.client.RestTemplate;

import cortica.test.value.*;

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

	/** Call You Tube and retrieve data. */
	@Async
	public Future<YouTubeFeed> makeCall(String query) throws IOException
	{
		return new AsyncResult<YouTubeFeed>(new RestTemplate().getForObject(CALL_URL, YouTubeFeed.class, query));
	}
}
