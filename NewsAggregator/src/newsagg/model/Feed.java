package newsagg.model;

import java.util.ArrayList;
import java.util.List;


public class Feed {
	
	private String categoryName;
	private String feedName;
	
	final List<Article> entries = new ArrayList<Article>();
	
	public Feed(String categoryName,String feedName ){
		
		this.categoryName = categoryName;
		this.feedName = feedName;
		
	}
	
	public String getCategoryName() {
		return categoryName;
	}

	public String getFeedName() {
		return feedName;
	}

	public List<Article> getArticles() {
        return entries;
    }

}
