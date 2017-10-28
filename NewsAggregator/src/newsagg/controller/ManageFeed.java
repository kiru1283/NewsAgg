package newsagg.controller;

import newsagg.model.FeedReader;
import newsagg.model.JSONReader;

import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

import newsagg.model.Article;
import newsagg.model.Feed;
import newsagg.model.JSONWriter;

public class ManageFeed {

	public void subscribeFeed(String category, String url) {

		// verify is feed is already added
		boolean feedexists = false;
		JSONArray arrFeed = viewFeeds();

		// System.out.println(arrFeed.size());
		if (arrFeed != null) {
			Iterator itarray = arrFeed.iterator();

			while (!feedexists && itarray.hasNext()) {

				JSONObject listObj = (JSONObject) itarray.next();

				if (listObj.get("url").equals(url)) {
					feedexists = true;
				}
			}
		}

		if (!feedexists) {
			// read to check and load feed data
			List<Article> articles = readFeed(category, url);

			if (articles.size() > 0) {
				
				// store the feed data
				JSONWriter jsonObj = new JSONWriter();
				jsonObj.jsonWrite(category, url);
				
			} else {
				System.out.println("This feed has no data");
			}

		} else {

			System.out.println("Feed url is already added");
		}

	}

	public JSONArray viewFeeds() {

		JSONReader readObj = new JSONReader();
		JSONArray arrFeed = readObj.jsonReader();

		// System.out.println(arrFeed);

		return arrFeed;

	}

	public List<Article> readFeed(String category, String url) {

		FeedReader parser = new FeedReader(category, url);
		Feed feed = parser.readFeed();
		// System.out.println(feed);

		List<Article> articles = feed.getArticles();

		for (Article message : articles) {

			System.out.println(message);

			// TODO method to display data
		}

		return articles;

	}

	public void removeFeed(String category, String url) {

	}

}
