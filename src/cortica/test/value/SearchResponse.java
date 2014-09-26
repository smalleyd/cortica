package cortica.test.value;

import java.io.Serializable;

/** Represents the full details of the search.
 * 
 * @author David Small
 * @version 1.0.1
 * @since 9/26/2014
 *
 * @param <T>
 */

public class SearchResponse<T> implements Serializable
{
	/** Constant - serial version UID. */
	public static final long serialVersionUID = 1L;

	/** Search term. */
	public String getSearchTerm() { return searchTerm; }
	private String searchTerm = null;

	/** Search Results. */
	public T getResults() { return results; }
	private T results = null;

	/** Populator.
	 * 
	 * @param searchTerm
	 * @param results
	 */
	public SearchResponse(String searchTerm, T results)
	{
		this.searchTerm = searchTerm;
		this.results = results;
	}
}
