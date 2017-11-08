package newsagg.model.tests;

import newsagg.model.Article;
import org.junit.Test;


import static org.junit.Assert.*;


public class ArticleTest {

	private Article artObj; 
	

	
	@Test
	public void testArticleid() {
		artObj = new Article();
		artObj.setArticleid(3);

		assertEquals("Articleid Not set in Article Object",artObj.getArticleid(), 3);
	}

	@Test
	public void testArticleDesc() {
		artObj = new Article();
		artObj.setDescription("NewsToday");

		assertEquals("Description Not set in Article Object",artObj.getDescription(), "NewsToday");
	}

	
}
