package newsagg.controller;

import newsagg.model.JSONReader;

import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.List;

import newsagg.model.Article;
import newsagg.model.Feed;
import newsagg.model.JSONWriter;
import newsagg.model.RSSReader;

public class ManageFeed {

	private boolean feedexists;
	private static final String dbfilename = "DB.json";

	public void subscribeFeed(String category, String url) {

		boolean removeFeed = false;

		JSONArray arrFeed = verifyFeedExists(category, url, removeFeed);

		if (!feedexists) {
			// read to check and load feed data from RSS
			List<Article> articles = readFeed(category, url);

			if (articles.size() > 0) {

				// store the feed data
				JSONWriter jsonObj = new JSONWriter(dbfilename);
				boolean writeFile = jsonObj.jsonWrite(arrFeed, category, url, removeFeed);
				if (!writeFile) {
					System.out.println("Error writing to file.");
				}

			} else {
				System.out.println("This feed has no data.");
			}

		} else {

			System.out.println("Feed url is already added.");
		}

	}

	private JSONArray verifyFeedExists(String category, String url, boolean remove) {

		// verify is feed is already added
		feedexists = false;
		JSONArray arrFeed = viewFeeds(false);

		// System.out.println(arrFeed.size());
		if (arrFeed != null) {
			Iterator itarray = arrFeed.iterator();

			while (!feedexists && itarray.hasNext()) {

				JSONObject listObj = (JSONObject) itarray.next();

				if (listObj.get("category").equals(category) && listObj.get("url").equals(url)) {

					if (remove) {
						itarray.remove();
					}
					feedexists = true;
				}
			}
		}
		return arrFeed;
	}

	public JSONArray viewFeeds(boolean printFeed) {

		JSONReader readObj = new JSONReader(dbfilename);
		JSONArray arrFeed = readObj.jsonReader();

		if (arrFeed != null && printFeed == true) {
			Iterator itarray = arrFeed.iterator();
			while (itarray.hasNext()) {
				JSONObject listObj = (JSONObject) itarray.next();
				System.out.println("Category: "+listObj.get("category").toString()+" Url: "+listObj.get("url").toString());
			}
		}
		return arrFeed;

	}

	public List<Article> readFeed(String category, String url) {

		RSSReader parser = new RSSReader(category, url);
		Feed feed = parser.readFeed();
		// System.out.println(feed);

		List<Article> articles = null;

		if (feed != null) {
			articles = feed.getArticles();
		}

		for (Article message : articles) {

			System.out.println(message);

			// TODO remove after display data in view
		}

		return articles;

	}

	public void removeFeed(String category, String url) {

		boolean removeFeed = true;

		JSONArray arrFeed = verifyFeedExists(category, url, removeFeed);
		JSONWriter jsonObj = new JSONWriter(dbfilename);

		boolean retVal = jsonObj.jsonWrite(arrFeed, category, url, removeFeed);

		if (!retVal) {
			System.out.println("Error writing to file.");
		}else {
			System.out.println("Feed removed successfully. Url:"+url);
		}
	}

}
