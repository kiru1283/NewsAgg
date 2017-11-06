package newsagg.model;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.xml.sax.InputSource;

//import com.sun.syndication.feed.synd.SyndCategoryImpl;
//import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
//import com.sun.syndication.feed.synd.SyndLinkImpl;
import com.sun.syndication.io.SyndFeedInput;
import newsagg.exceptions.RSSException;

public class RSSReader {

	private final String url;
	private final String category;

	public RSSReader(String category, String feedUrl) {
		this.category = category;
		this.url = feedUrl;
	}

	public SyndFeed getSyndFeedForUrl() throws RSSException {

		SyndFeed feed = null;
		InputStream inpstr = null;
		try {
			URLConnection openConnection = new URL(url).openConnection();
			if ("gzip".equals(openConnection.getContentEncoding())) {
				inpstr = new GZIPInputStream(inpstr);
			}

			InputSource source = new InputSource(openConnection.getInputStream());

			SyndFeedInput input = new SyndFeedInput();

			feed = input.build(source);

			if (inpstr != null)
				inpstr.close();

		} catch (Exception e) {
			throw new RSSException(
					"Exception occured when building the feed from the url. Error Msg: " + e.getMessage());
		}

		return feed;
	}

	@SuppressWarnings("unchecked")
	public Feed readFeed() throws  RSSException {
		Feed feedObj = null;

		SyndFeed feed = getSyndFeedForUrl();

		feedObj = new Feed(category, "");
		int articleid = 0;

		for (SyndEntry entry : (List<SyndEntry>) feed.getEntries()) {

			SyndContentImpl descrip = (SyndContentImpl) entry.getDescription();

			/*
			 * String display = "Entry:"; display += "\ntitle:"+entry.getTitle(); display +=
			 * "\nlink:"+entry.getLink(); display += "\nauthor:"+entry.getAuthor(); display
			 * += "\npublished:"+entry.getPublishedDate(); display +=
			 * "\nupdated:"+entry.getUpdatedDate(); display +=
			 * "\ndescription:"+descrip.getValue().replaceAll("\\<[^>]*>", "");
			 * 
			 * display += "\ncontent size:"+entry.getContents().size(); if
			 * (entry.getContents().size()==1) { SyndContentImpl imp =
			 * (SyndContentImpl)entry.getContents().get(0); display +=
			 * "\ncontent value:"+imp.getValue(); } display += "\n";
			 * System.out.println(display);
			 */

			Article message = new Article();
			message.setCreator(entry.getAuthor());
			message.setDescription(descrip.getValue().replaceAll("\\<[^>]*>", ""));

			message.setGuid(entry.getUri());

			message.setLink(entry.getLink());
			message.setTitle(entry.getTitle());
			message.setPubdate(entry.getPublishedDate().toString());
			message.setArticleid(articleid += 1);

			feedObj.getArticles().add(message);

		}

		return feedObj;
	}
}
