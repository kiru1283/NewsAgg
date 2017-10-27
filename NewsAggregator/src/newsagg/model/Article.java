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
	private String title;
	private String creator;
	private String pubdate;
	private String guid;
	

	public Article(){
//		this.description = description;
//		this.link = link;
//		this.image = image;
//		this.pubdate = pubdate;
//		this.creator = creator;
		
		articleid++;
		
	}
	
	public int getArticleid() {
		return articleid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getPubdate() {
		return pubdate;
	}

	public void setPubdate(String pubdate) {
		this.pubdate = pubdate;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}
		
	 @Override
	    public String toString() {
	        return "Article [title=" + title + ", description=" + description
	                + ", link=" + link + ", author=" + creator + ", guid=" + guid
	                + ", pubdate=" + pubdate + "]";
	    }

	
}
