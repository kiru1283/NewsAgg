package newsagg.model;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
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

public class RSSReader {

	private final String url;
	private final String category;
	// final URL url;

	public RSSReader(String category, String feedUrl) {
		this.category = category;
		this.url = feedUrl;	
	}

	public SyndFeed getSyndFeedForUrl() throws IOException {

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

			// System.out.println(feed);

		} catch (Exception e) {
			System.out.println("Exception occured when building the feed object out of the url" + e.getMessage());
		} finally {
			if (inpstr != null)
				inpstr.close();
		}

		return feed;
	}

	@SuppressWarnings("unchecked")
	public Feed readFeed() {
		Feed feedObj = null;

		try {
			SyndFeed feed = getSyndFeedForUrl();

			feedObj = new Feed(category, "");

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

				feedObj.getArticles().add(message);

			}

		} catch (Exception e) {
			//System.out.println("Exception occured when building the feed object out of the url" + e.getMessage());
		}

		return feedObj;
	}
}
