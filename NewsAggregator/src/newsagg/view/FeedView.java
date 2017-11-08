package newsagg.view;

import java.util.List;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import newsagg.controller.LoginValidation;
import newsagg.controller.ManageFeed;
import newsagg.exceptions.FeedException;
import newsagg.exceptions.JSONFileException;
import newsagg.exceptions.RSSException;

public class FeedView {

	private Scanner scanner;
	//private String inputUser;
	private ManageFeed manObj;
	private ArticleView artObj;
	private JSONArray userarrFeed;
	
	public FeedView() {
		manObj = new ManageFeed();
		scanner = new Scanner(System.in);
		artObj = new ArticleView();
		

	}

	// Method to add subscribed feed URL to JSON file
	public void subscribeFeed(String inputUser) {

		

		System.out.println("Please enter category:");
		String category = scanner.nextLine().trim();
		System.out.println("Please enter feed url:");
		String url = scanner.nextLine().trim();

		try {

			if (manObj.subscribeFeed(category, url, inputUser)) {
				System.out
						.println("You have successfully subscribed to feed url: "
								+ url);
				System.out.println("");
			} else {
				System.out.println("Unable to subscribe to feed url: " + url);
				System.out.println("");
			}

		} catch (FeedException | JSONFileException | RSSException e) {
			System.out.println(e.getMessage());
		}
	}



	// Method to read a feed which has been subscribed by the user
	public void readFeed(String inputUser) {

		System.out.println("");
		System.out.println("--------------------");
		System.out.println("| SUB-MENU - FEEDS  |");
		System.out.println("--------------------");
		System.out.println("Please Select One Option:");
		System.out.println("L -> List Articles in a Feed");
		System.out.println("R -> Return to Main Menu");
		String selection = scanner.nextLine().trim();

		if (selection.toUpperCase().trim().equals("L")) {

			boolean nofeed = userFeeds(inputUser);

			if (!nofeed) {
				System.out.println("");
				System.out.println("Please enter feed number to read its articles.");
				System.out.println("Feed No. :");
				// String category = scanner.nextLine().trim();
				int index = 0;

				try {
					index = Integer.parseInt(scanner.nextLine().trim());
				} catch (NumberFormatException e) {
					System.out.println("Invalid Feed number.");
					return;
				}

				if (index > 0 && index <= userarrFeed.size()) {
					JSONObject listObj = (JSONObject) userarrFeed.get(index - 1);
					String category = listObj.get("category").toString();
					String url = listObj.get("url").toString();
					List<String> articles = null;

					try {
						articles = manObj.readFeed(category, url, inputUser);
						// print articles in the feed
						for (String message : articles) {
							System.out.println(message.toString());
						}

						System.out.println("");

					} catch (FeedException | JSONFileException | RSSException e) {
						System.out.println(e.getMessage());
					}

					if (articles != null) {
						boolean isfavourite = false;
						artObj.viewArticles(articles, category, isfavourite,inputUser);
					}

				} else {
					System.out.println("Invalid Feed number.");
					System.out.println("");
					return;
				}

			} else {
				System.out.println("Empty feed list. No feeds subscribed yet !!");
				System.out.println("");
			}
		} else if (selection.toUpperCase().trim().equals("R")) {
			return;
		} else {
			System.out.println("Invalid Option Entered !!");
			System.out.println("");
		}

	}

	
	
	// method to remove unsubscribed feed URL from the JSON file
	public void unsubscribeFeed(String inputUser) {

		boolean nofeed = userFeeds(inputUser);

		if (!nofeed) {
			System.out.println("");

			System.out.println("Please enter feed number to Unsubscribe.");
			System.out.println("Feed No. :");

			int index = 0;

			try {
				index = Integer.parseInt(scanner.nextLine().trim());
			} catch (NumberFormatException e) {
				System.out.println("Invalid Feed number.");
				return;
			}

			if (index > 0 && index <= userarrFeed.size()) {
				JSONObject listObj = (JSONObject) userarrFeed.get(index - 1);
				String category = listObj.get("category").toString();
				String url = listObj.get("url").toString();

				try {
					if (!manObj.removeFeed(category, url, inputUser)) {
						System.out.println("Error writing to file.");
					} else {
						System.out.println("Feed removed successfully. Url:"
								+ url);
					}
				} catch (FeedException | JSONFileException e) {
					System.out.println(e.getMessage());
				}
			} else {
				System.out.println("Invalid Feed number.");
				System.out.println("");
				return;
			}
		} else {
			System.out.println("Empty feed list. No feeds subscribed yet !!");
			System.out.println("");
		}

	}

	// Method to remove feeds which are not linked to the current user

		private boolean userFeeds(String inputUser) {

			boolean nofeed = true;

			JSONArray userFeeds = null;
			try {

				userFeeds = manObj.viewFeeds();

			} catch (JSONFileException e) {
				System.out.println(e.getMessage());
			}
			if (userFeeds != null) {

				userarrFeed = manObj.userFeeds(userFeeds, inputUser);
				if (userarrFeed != null) {
					int i = 1;
					for (Object Obj : userarrFeed) {
						JSONObject listObj = (JSONObject) Obj;
						if (i == 1) {
							System.out.println("Following are the Feeds subscribed:");
							System.out.println("");
						}
						System.out.println("Feed No." + i + " | Category: " + listObj.get("category").toString()
								+ " | Url: " + listObj.get("url").toString());
						nofeed = false;
						i++;
					}
				}

			} else {
				System.out.println("No feeds subscribed.Please subscribe to read a feed.");
				System.out.println("");
			}

			return nofeed;
		}
}
