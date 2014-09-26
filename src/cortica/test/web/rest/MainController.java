package cortica.test.web.rest;

import java.util.*;
import java.util.concurrent.Future;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.*;

import cortica.test.service.*;
import cortica.test.value.*;

/** RESTful controller that aggregates search results.
 * 
 * @author David Small
 * @version 1.0.1
 * @since 9/24/2014
 *
 */

@RestController
@RequestMapping(value="/main")
public class MainController
{
	@Resource GoogleNewsSearch googleNewsSearch;
	@Resource YouTubeSearch youTubeSearch;

	/** Performs the search.
	 * 
	 *  NOTE: must specify 'produces="application/json"' because once the Jackson DataFormat library
	 *        (com.fasterxml.jackson.dataformat) is included the default output is XML when testing the
	 *        call from a browser.
	 *
	 * @param q
	 * @return
	 * @throws Exception
	 */
	// @ResponseBody
	@RequestMapping(value="/search", method=RequestMethod.GET, produces="application/json")
	public Map<String, Object> search(@RequestParam(value="q") String q) throws Exception
	{

		Future<GoogleNewsFeed> googleNewsResult = googleNewsSearch.makeCall(q);
		Future<YouTubeFeed> youTubeResult = youTubeSearch.makeCall(q);

		Map<String, Object> results = new HashMap<>(2);
		results.put("NewsResponse", new SearchResponse<GoogleNewsFeed>(q, googleNewsResult.get()));
		results.put("YTResponse", new SearchResponse<YouTubeFeed>(q, youTubeResult.get()));

		return results;
	}
}
