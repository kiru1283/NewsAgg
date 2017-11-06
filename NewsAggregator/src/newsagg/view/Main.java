package newsagg.view;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import newsagg.controller.LoginValidation;
import newsagg.controller.ManageFeed;
import newsagg.controller.ShareArticle;
import newsagg.controller.ViewArticle;
import newsagg.exceptions.ArticleException;
import newsagg.exceptions.AuthenticationException;
import newsagg.exceptions.FeedException;
import newsagg.exceptions.JSONFileException;
import newsagg.exceptions.RSSException;

/**
 * 
 * @author Kiruthiga
 * @category Main Class acting as view
 *
 */
public class Main {
	private Scanner scanner;
	private LoginValidation loginObj;
	private ManageFeed manObj;
	private String inputUser;
	private JSONArray userarrFeed;

	/**
	 * constructor to initialize the scanner and controller classes
	 */
	public Main() {
		scanner = new Scanner(System.in);
		loginObj = new LoginValidation();
		manObj = new ManageFeed();
	}

	/**
	 * Main method for program start point
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Main main = new Main();
		main.mainLoop();
	}

	/**
	 * Method to call controller class methods based on user input
	 */
	public void mainLoop() {

		System.out.println("Welcome to the FeedBook Application !!");

		while (true) {

			boolean validUser = false;

			while (!validUser) {

				System.out.println("To Start FeedBook Please Enter : ");
				System.out.println("C -> To Create New User Account");
				System.out.println("L -> To Login With Existing User Account");

				String inputOption = scanner.nextLine();

				if (inputOption.toUpperCase().trim().equals("C")) {

					validUser = createNewUser();

				} else if (inputOption.toUpperCase().trim().equals("L")) {

					validUser = loginUser();

				} else {
					System.out.println("Invalid Option Entry!!");
				}

			}

			// for Valid users choose operation
			if (validUser) {
				userOperations();
			}
		}
	}

	// Method to create new User account
	private boolean createNewUser() {

		System.out.println("Please enter username:");
		inputUser = scanner.nextLine().trim();
		System.out.println("Please enter password:");
		String inputPass = scanner.nextLine().trim();

		boolean validUser = false;

		if (inputUser.equals("")) {
			System.out.println("Blank Username is not allowed !!");
		}
		if (inputPass.equals("")) {
			System.out.println("Blank Password is not allowed !!");
		}

		if (!inputUser.equals("") && !inputPass.equals("")) {
			// for new users
			try {
				if (loginObj.createUser(inputUser, inputPass)) {
					System.out.println("User account created successfully!!");
					validUser = true;
				} else {
					System.out.println("Error while creating User account!!");
				}
			} catch (AuthenticationException | JSONFileException e) {
				System.out.println(e.getMessage());
			}
		}

		return validUser;
	}

	// Method to validate the userid and password of an existing user account
	private boolean loginUser() {

		boolean validUser = false;
		System.out.println("Please enter username:");
		inputUser = scanner.nextLine().trim();
		System.out.println("Please enter password:");
		String inputPass = scanner.nextLine().trim();

		if (inputUser.equals("")) {
			System.out.println("Blank Username is not allowed !!");
		}
		if (inputPass.equals("")) {
			System.out.println("Blank Password is not allowed !!");
		}

		if (!inputUser.equals("") && !inputPass.equals("")) {
			// for existing user
			try {
				if (loginObj.validateUser(inputUser, inputPass))

				{
					System.out.println("Logged in!");
					System.out.println("");
					validUser = true;

				} else {
					System.out.println("Incorrect username or password !!");
					System.out.println("");

				}
			} catch (AuthenticationException | JSONFileException e) {
				System.out.println(e.getMessage());
			}
		}

		return validUser;
	}

	// Method used for processing user input after login
	private void userOperations() {

		String loop = "GO";

		while (!loop.equals("LOGOUT")) {

			System.out.println("Please Enter Any One Option: ");
			System.out.println("S -> To Subscribe a feed");
			System.out.println("R -> Read a feed");
			System.out.println("U -> To UnSubscribe a feed");
			System.out.println("X -> Exit the application");

			String inputVal = scanner.nextLine();

			if (inputVal.toUpperCase().trim().equals("S")) {

				subscribeFeed();

			} else if (inputVal.toUpperCase().trim().equals("R")) {

				readFeed();

			} else if (inputVal.toUpperCase().trim().equals("U")) {

				unsubscribeFeed();
			} else if (inputVal.toUpperCase().trim().equals("X")) {

				loop = "LOGOUT";

			} else {
				System.out.println("Invalid Option Entry!!");
			}

		}

		System.out.println("Logged Out from FeedBook Application!!");
		System.out.println("");

	}

	// Method to add subscribed feed URL to JSON file
	private void subscribeFeed() {

		System.out.println("Please enter category:");
		String category = scanner.nextLine().trim();
		System.out.println("Please enter feed url:");
		String url = scanner.nextLine().trim();

		try {

			if (manObj.subscribeFeed(category, url, inputUser)) {
				System.out.println("You have successfully subscribed to feed url: " + url);
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
	private void readFeed() {

		boolean nofeed = userFeeds();

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
					viewArticles(articles);
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

	// method to remove unsubscribed feed URL from the JSON file
	private void unsubscribeFeed() {

		boolean nofeed = userFeeds();

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
						System.out.println("Feed removed successfully. Url:" + url);
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
	@SuppressWarnings("unchecked")
	private boolean userFeeds() {

		boolean nofeed = true;

		userarrFeed = null;
		try {

			userarrFeed = manObj.viewFeeds();

		} catch (JSONFileException e) {
			System.out.println(e.getMessage());
		}
		if (userarrFeed != null) {

			Iterator<JSONObject> itarray = userarrFeed.iterator();
			int i = 1;
			while (itarray.hasNext()) {
				JSONObject listObj = (JSONObject) itarray.next();
				if (listObj.get("username").equals(inputUser)) {
					if (i == 1) {
						System.out.println("Following are the Feeds subscribed:");
						System.out.println("");
					}
					System.out.println("Feed No." + i + " | Category: " + listObj.get("category").toString()
							+ " | Url: " + listObj.get("url").toString());
					nofeed = false;
					i++;
				} else {
					itarray.remove();
				}
			}
		} else {
			System.out.println("No feeds subscribed.Please subscribe to read a feed.");
			System.out.println("");
		}

		return nofeed;
	}

	//Method to open article in browser or share by email.
	private void viewArticles(List<String> articles) {

		System.out.println("Please Enter any One Option ");
		System.out.println("O -> Open Article in browser");
		System.out.println("E -> Share Article by email");
		System.out.println("R -> Return to Main Menu");
		String options = scanner.nextLine().trim();

		if (options.toUpperCase().trim().equals("R")) {
			return;
		}
		if (options.toUpperCase().trim().equals("O") || options.toUpperCase().trim().equals("E")) {
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

				//open article url in browser
				if (options.toUpperCase().trim().equals("O")) {
					
					ViewArticle objView = new ViewArticle(url.trim());

					try {
						objView.openArticle();
					} catch (ArticleException e) {
						System.out.println(e.getMessage());
					}
				}else
				{
					//share article by email
					System.out.println("Enter email id of Reciever: ");
					String toAddress = scanner.nextLine().trim();
					//TODO: validate emailid input
					
					
					ShareArticle shareObj = new ShareArticle();
					shareObj.shareFeed(url.trim(),toAddress,inputUser);
				}

			} else {
				System.out.println("Invalid Article Number.");
			}
		} else {
			System.out.println("Invalid Option Entry!!");
		}

	}

}
