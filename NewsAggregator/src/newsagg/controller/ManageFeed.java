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

	public void subscribeFeed(String category, String url) {

		boolean removeFeed =false;
		
		JSONArray arrFeed = verifyFeedExists(category,url,removeFeed);

		if (!feedexists) {
			// read to check and load feed data from RSS
			List<Article> articles = readFeed(category, url);

			if (articles.size() > 0) {
				
				// store the feed data
				JSONWriter jsonObj = new JSONWriter();
				jsonObj.jsonWrite(arrFeed,category, url,removeFeed);
				
			} else {
				System.out.println("This feed has no data");
			}

		} else {

			System.out.println("Feed url is already added");
		}

	}

	private JSONArray verifyFeedExists(String category, String url, boolean remove) {
		
		// verify is feed is already added
				feedexists = false;
				JSONArray arrFeed = viewFeeds();

				// System.out.println(arrFeed.size());
				if (arrFeed != null) {
					Iterator itarray = arrFeed.iterator();

					while (!feedexists && itarray.hasNext()) {

						JSONObject listObj = (JSONObject) itarray.next();

						if (listObj.get("category").equals(category) && listObj.get("url").equals(url)) {
							
							if(remove) {
								itarray.remove();
							}
							feedexists = true;
						}
					}
				}
				return arrFeed;
	}
	
	public JSONArray viewFeeds() {

		JSONReader readObj = new JSONReader();
		JSONArray arrFeed = readObj.jsonReader();

		// System.out.println(arrFeed);

		return arrFeed;

	}

	public List<Article> readFeed(String category, String url) {

		//FeedReader parser = new FeedReader(category, url);
		
		RSSReader parser = new RSSReader(category, url);
		Feed feed = parser.readFeed();
		// System.out.println(feed);

		List<Article> articles = null;
		
		if(feed != null) {
			articles = feed.getArticles();
		}

		for (Article message : articles) {

			System.out.println(message);

			// TODO remove after display data in view
		}

		return articles;

	}

	public void removeFeed(String category, String url) {
		
		boolean removeFeed =true;

		JSONArray arrFeed = verifyFeedExists(category,url,removeFeed);
		JSONWriter jsonObj = new JSONWriter();
			
		jsonObj.jsonWrite(arrFeed,category, url,removeFeed);
			
		
	}

}
