package cortica.test.value;

import java.io.Serializable;

/** Value object that represents a single news result item.
 * 
 * @author David Small
 * @version 1.0.1
 * @since 9/28/2014
 *
 */

public class NewsItem implements Serializable
{
	/** Constant - serial version UID. */
	public static final long serialVersionUID = 1L;

	/** Represents the news item title. */
	public String getTitle() { return title; }
	private String title = null;

	/** Represents the link to the news item. */
	public String getLink() { return link; }
	private String link = null;

	/** Represents the publication date of the news items. */
	public String getPubDate() { return pubDate; }
	private String pubDate = null;

	/** Represents the news item author. */
	public String getAuthor() { return author; }
	private String author = null;

	/** Populator.
	 * 
	 * @param title
	 * @param link
	 * @param pubDate
	 * @param author
	 */
	public NewsItem(String title, String link, String pubDate, String author)
	{
		this.title = title;
		this.link = link;
		this.pubDate = pubDate;
		this.author = author;
	}

	/** Populator.
	 * 
	 * @param title
	 * @param link
	 * @param pubDate
	 */
	public NewsItem(String title, String link, String pubDate)
	{
		this(title, link, pubDate, null);
	}
}
