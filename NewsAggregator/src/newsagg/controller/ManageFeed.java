package newsagg.controller;

import newsagg.model.JSONReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.List;

import newsagg.exceptions.FeedException;
import newsagg.exceptions.JSONFileException;
import newsagg.exceptions.RSSException;
import newsagg.model.Article;
import newsagg.model.Feed;
import newsagg.model.JSONWriter;
import newsagg.model.RSSReader;

public class ManageFeed {

	// private boolean feedexists;
	private static final String dbfilename = "DB.json";

	public boolean subscribeFeed(String category, String url, String username)
			throws FeedException, JSONFileException, IOException, RSSException {

		boolean removeFeed = false;
		boolean feedexists = false;

		boolean writeFile = false;

		JSONArray arrFeed = viewFeeds();

		if (arrFeed != null) {

			feedexists = verifyFeedExists(arrFeed, category, url, removeFeed, username);
		}

		if (!feedexists) {
			// read to check and load feed data from RSS
			List<String> articles = readFeedContent(category, url);

			if (articles.size() > 0) {

				// store the feed data
				JSONWriter jsonObj = new JSONWriter(dbfilename);
				writeFile = jsonObj.jsonWrite(arrFeed, category, url, removeFeed, username);

			} else {

				throw new FeedException("This feed has no data.");
			}

		} else {

			throw new FeedException("Feed url is already added.");
		}

		return writeFile;

	}

	@SuppressWarnings("rawtypes")
	private boolean verifyFeedExists(JSONArray arrFeed, String category, String url, boolean remove, String username) {

		// verify is feed is already added
		boolean feedexists = false;

		// System.out.println(arrFeed.size());
		Iterator itarray = arrFeed.iterator();

		while (!feedexists && itarray.hasNext()) {

			JSONObject listObj = (JSONObject) itarray.next();

			if (listObj.get("category").equals(category) && listObj.get("url").equals(url)
					&& listObj.get("username").equals(username)) {

				if (remove) {
					itarray.remove();
				}
				feedexists = true;
			}
		}

		return feedexists;
	}

	
	public JSONArray viewFeeds() throws JSONFileException {

		JSONReader readObj = new JSONReader(dbfilename);

		JSONArray arrFeed = readObj.jsonReader();

/*		if (arrFeed != null) {

			Iterator itarray = arrFeed.iterator();

			while (itarray.hasNext()) {

				JSONObject listObj = (JSONObject) itarray.next();

				if (!listObj.get("username").equals(username)) {

					itarray.remove();

				}
			}
		}
*/
		return arrFeed;

	}

	public List<String> readFeedContent(String category, String url) throws RSSException {

		RSSReader parser = new RSSReader(category, url);
		Feed feed = parser.readFeed();

		List<Article> articles = null;
		List<String> articleDetails = new ArrayList<>();

		if (feed != null) {
			articles = feed.getArticles();
		}

		for (Article message : articles) {
			articleDetails.add(message.toString());
		}

		return articleDetails;

	}

	public List<String> readFeed(String category, String url, String username)
			throws FeedException, JSONFileException, RSSException {

		boolean feedexists = false;

		JSONArray arrFeed = viewFeeds();

		if (arrFeed != null) {

			feedexists = verifyFeedExists(arrFeed, category, url, false, username);
		}

		if (feedexists) {
			RSSReader parser = new RSSReader(category, url);
			Feed feed = parser.readFeed();
			// System.out.println(feed);

			List<Article> articles = null;
			List<String> articleDetails = new ArrayList<>();

			if (feed != null) {
				articles = feed.getArticles();
			}

			for (Article message : articles) {
				articleDetails.add(message.toString());
			}

			return articleDetails;
		} else {
			throw new FeedException(
					"Invalid Feed url or category. Please verify your entry and subscribe if its a new Feed.");
		}

	}

	public boolean removeFeed(String category, String url, String username) throws FeedException, JSONFileException {

		boolean removeFeed = true;
		boolean feedexists = false;
		boolean retVal = false;

		JSONArray arrFeed = viewFeeds();

		if (arrFeed != null) {
			feedexists = verifyFeedExists(arrFeed, category, url, removeFeed, username);
		}
		if (feedexists) {
			JSONWriter jsonObj = new JSONWriter(dbfilename);

			retVal = jsonObj.jsonWrite(arrFeed, category, url, removeFeed, username);

		} else {
			throw new FeedException("Feed can not be unsubscribed as url or category is incorrect");
		}

		return retVal;

	}

}
