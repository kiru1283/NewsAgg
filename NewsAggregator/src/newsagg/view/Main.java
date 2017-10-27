package newsagg.view;

import newsagg.controller.ManageFeed;
import newsagg.model.JSONWriter;
import newsagg.model.JSONReader;

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
		
		//JSONWriter jsonObj = new JSONWriter();
		//jsonObj.jsonwrite();
		
		JSONReader readObj = new JSONReader();
		readObj.reader();

	}

}
