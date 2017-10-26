package newsagg.controller;

import newsagg.model.XMLReader;
import newsagg.model.Feed;
import newsagg.model.Article;


public class ManageFeed {
	
	public void subscribeFeed(String category,String url)
	{
		 XMLReader parser = new XMLReader(category,url);	                
	        Feed feed = parser.readFeed();
	        System.out.println(feed);
	        
	        for (Article message : feed.getArticles()) {
	            System.out.println(message);

	        }
		
	}
	

}
