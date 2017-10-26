package newsagg.model;

/**
 * @author Kiruthiga
 * Class for Articles in a Feed
 *
 */
public class Article {
	private static int articleid = 0;
	private String description;
	private String link;
	private String image;
	private String creator;
	private String pubdate;
	
	public Article(String description,String link,String image,String creator,String pubdate){
		this.description = description;
		this.link = link;
		this.image = image;
		this.pubdate = pubdate;
		this.creator = creator;
		articleid++;
		
	}
	
	public int getArticleid() {
		return articleid;
	}
		
	public String getDescription() {
		return description;
	}
	
	public String getLink() {
		return link;
	}
	
	public String getImage() {
		return image;
	}
	
	public String getCreator() {
		return creator;
	}
	
	public String getPubdate() {
		return pubdate;
	}

	
}
