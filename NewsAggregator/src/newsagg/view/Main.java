package newsagg.view;

import java.util.List;

import org.json.simple.JSONArray;

import newsagg.controller.ManageFeed;



public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ManageFeed manObj = new ManageFeed();
		
		//to add feed and store in JSON
		manObj.subscribeFeed("news","https://www.yahoo.com/news/rss/tech");
		
		manObj.subscribeFeed("news","http://www.nytimes.com/services/xml/rss/nyt/HomePage.xml");
		
				//"http://www.vogella.com/article.rss");
				//manObj.subscribeFeed("news","http://sverigesradio.se/sida/default.aspx?programid=4916");
				//"https://www.yahoo.com/news/rss/tech"
				//"http://www.nytimes.com/services/xml/rss/nyt/HomePage.xml");
		
		//to show all feeds added so far
		JSONArray jsarray = manObj.viewFeeds();
		//TODO: bind values of jsonarray to tree view
		
		//to show the articles in a feed
		List articles = manObj.readFeed("news","https://www.yahoo.com/news/rss/tech");
		//TODO: bind values of articles to grid
		
		//to delete a existing feed
		manObj.removeFeed("news","https://www.yahoo.com/news/rss/tech");

	}

}
