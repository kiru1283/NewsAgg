package newsagg.view;

import newsagg.controller.ManageFeed;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ManageFeed manObj = new ManageFeed();
		manObj.subscribeFeed("news","https://www.yahoo.com/news/rss/tech");
		
		//"http://www.vogella.com/article.rss");
		//"http://sverigesradio.se/sida/default.aspx?programid=4916");
		//"https://www.yahoo.com/news/rss/tech"
		//"http://www.nytimes.com/services/xml/rss/nyt/HomePage.xml");

	}

}
