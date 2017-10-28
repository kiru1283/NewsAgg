package newsagg.model;

import java.util.ArrayList;
import java.util.List;
//import org.json.simple.JSONObject;


public class Feed {
	
	private String categoryName;
	private String feedName;
	
	private List<Article> entries = new ArrayList<Article>();
	//private JSONObject obj = new JSONObject();

	
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
