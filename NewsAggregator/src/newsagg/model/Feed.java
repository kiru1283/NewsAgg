package newsagg.model;

public class Feed {
	
	private String categoryName;
	private String feedName;
	
	
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


}
