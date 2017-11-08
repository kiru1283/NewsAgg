package newsagg.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import newsagg.controller.MarkArticle;
import newsagg.controller.ShareArticle;
import newsagg.controller.ViewArticle;
import newsagg.exceptions.ArticleException;
import newsagg.exceptions.JSONFileException;
import newsagg.exceptions.ShareException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


/**
 * Class to create the Article sub menu and provided article operations
 * @author Kiruthiga
 *
 */
public class ArticleView {


	private MarkArticle articleObj;
	private Scanner scanner;
	
	/**
	 * Constructor Method to initialize the controller
	 */
	public ArticleView() {
		
		articleObj = new MarkArticle();
		scanner = new Scanner(System.in);
	}
	


	/**
	 * Method to open article in browser or share by email.
	 * @param articles - list of articles to be displayed
	 * @param category - category of Feed
	 * @param isfavourite - true we need to view favourite articles 
	 * @param inputUser - userid of the logged in user
	 */
	public void viewArticles(List<String> articles, String category, boolean isfavourite, String inputUser) {

		String loop = "Z";

		while (!loop.equals("R")) {
			System.out.println("------------------------");
			System.out.println("| SUB-MENU - ARTICLES  |");
			System.out.println("------------------------");
			System.out.println("Please Enter An Option:");
			System.out.println("O -> Open Article in browser");
			System.out.println("E -> Share Article by email");
			if (isfavourite == false) {
				System.out.println("M -> Mark Article as favourite");
			} else {
				System.out.println("U -> UnMark Article from favourites");
			}
			System.out.println("R -> Return to Main Menu");
			String options = scanner.nextLine().trim();

			if (options.toUpperCase().trim().equals("O") || options.toUpperCase().trim().equals("E")
					|| options.toUpperCase().trim().equals("M") || options.toUpperCase().trim().equals("U")) {
				System.out.println("Enter Article No.: ");
				int index = -1;

				try {
					index = Integer.parseInt(scanner.nextLine().trim());
				} catch (NumberFormatException e) {
					System.out.println("Invalid Article Number.");
					return;
				}

				// return to main menu
				if (index > 0 && index <= articles.size()) {

					// JSONObject listObj = (JSONObject) ;
					String article = articles.get(index - 1).toString();
					int beginIndex = article.indexOf("link=");
					int endIndex = article.indexOf(", creator");

					String url = article.substring(beginIndex + 5, endIndex);

					if (options.toUpperCase().trim().equals("M")) {
						// save an article as favorite
						try {

							articleObj.saveArticle(category, url, inputUser, false);

							System.out.println("Article Marked as Favourite with URL " + url);

							System.out.println("");
						} catch (JSONFileException e) {
							System.out.println(e.getMessage());
						}

					} else if (options.toUpperCase().trim().equals("O")) {
						// open article url in browser
						ViewArticle objView = new ViewArticle(url.trim());

						try {

							if (objView.openArticle() == true) {
								System.out.println("Article opened in browser with URL " + url);
							} else {
								System.out.println("Error while opening article with url: " + url);
							}

							System.out.println("");
						} catch (ArticleException e) {
							System.out.println(e.getMessage());
						}
					} else if (options.toUpperCase().trim().equals("E")) {
						// share article by email
						System.out.println("Enter email id of Reciever: ");
						String toAddress = scanner.nextLine().trim();

						ShareArticle shareObj = new ShareArticle();

						try {

							if (shareObj.shareFeed(url.trim(), toAddress, inputUser) == true) {
								System.out.println("Mail successfully sent to " + toAddress);
							} else {
								System.out.println("Error sending mail to " + toAddress);
							}

							System.out.println("");
						} catch (ShareException e) {
							System.out.println(e.getMessage());
						}

					} else if (options.toUpperCase().trim().equals("U") && isfavourite) {
						try {

							int begin = article.indexOf("category=");

							String artcategory = article.substring(begin + 9);

							JSONArray modifiedArr = articleObj.saveArticle(artcategory, url, inputUser, true);
							if (modifiedArr.size() == 0) {
								// return to main menu if all articles removed
								loop = "R";
							}
							System.out.println("Article Removed from Favourites. Article url:" + url);
							System.out.println("");
						} catch (JSONFileException e) {
							System.out.println(e.getMessage());
							System.out.println("");
						}
					}
				} else {
					System.out.println("Invalid Article Number.");
					System.out.println("");
				}
			} else if (options.toUpperCase().trim().equals("R")) {
				// to return to main menu
				loop = "R";
			} else {
				System.out.println("Invalid Option Entered!!");
				System.out.println("");
			}
		}

	}

	/**
	 * Method to list the article marked as favourites
	 * @param inputUser - userid of the logged in user
	 */
	public void viewFavourites(String inputUser) {

		JSONArray userArticle = null;
		List<String> articles = new ArrayList<>();
		try {

			userArticle = articleObj.viewFavourites(inputUser);

		} catch (JSONFileException e) {
			System.out.println(e.getMessage());
		}
		if (userArticle != null) {

			if (userArticle.size() > 0) {
				int i = 1;
				for (Object Obj : userArticle) {
					JSONObject listObj = (JSONObject) Obj;
					if (i == 1) {
						System.out.println("Following are the Favourite Artciles:");
						System.out.println("");
					}
					System.out.println("Article No." + i + " | Category: " + listObj.get("category").toString()
							+ " | Url: " + listObj.get("url").toString());
					String urltext = "link=" + listObj.get("url").toString() + ", creator=''" + ", category="
							+ listObj.get("category").toString();
					articles.add(urltext);
					i++;
				}

				System.out.println("");
				boolean isfavourite = true;
				viewArticles(articles, "", isfavourite,inputUser);
			} else {
				System.out.println("No Articles Marked as Favourite.");
				System.out.println("");
			}
		} else {
			System.out.println("No Articles Marked as Favourite.");
			System.out.println("");
		}
	}

}
