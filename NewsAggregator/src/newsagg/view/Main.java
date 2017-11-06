package newsagg.view;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import newsagg.controller.LoginValidation;
import newsagg.controller.ManageFeed;
import newsagg.exceptions.AuthenticationException;
import newsagg.exceptions.FeedException;
import newsagg.exceptions.JSONFileException;
import newsagg.exceptions.RSSException;

public class Main {
	private Scanner scanner;
	private LoginValidation loginObj;
	private ManageFeed manObj;
	private String inputUser;

	public Main() {
		scanner = new Scanner(System.in);
		loginObj = new LoginValidation();
		manObj = new ManageFeed();
	}

	public static void main(String[] args) {
		Main main = new Main();
		main.mainLoop();
	}

	public void mainLoop() {

		System.out.println("Welcome to the NewsBoard Application !!");

		while (true) {
			// Scanner scanner = new Scanner(System.in);
			
			boolean validUser = false;
			// LoginValidation loginObj = new LoginValidation();

			while (!validUser) {

				System.out.println("To Start NewsBoard Application Please Enter : ");
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
			try {
				if (loginObj.validateUser(inputUser, inputPass))

				{
					System.out.println("Logged in!");
					validUser = true;

				} else {
					System.out.println("Incorrect username or password !!");

				}
			} catch (AuthenticationException | JSONFileException e) {
				System.out.println(e.getMessage());
			}
		}

		return validUser;
	}

	private void userOperations() {

		// System.out.println("Logged in!");
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

		System.out.println("Logged Out from NewsBoard Application!!");
		System.out.println("");

		/*
		 * // to add feed and store in JSON manObj.subscribeFeed("news",
		 * "https://www.yahoo.com/news/rss/tech");
		 * 
		 * manObj.subscribeFeed("news",
		 * "http://www.nytimes.com/services/xml/rss/nyt/HomePage.xml");
		 * 
		 * // "http://www.vogella.com/article.rss"); //
		 * manObj.subscribeFeed("news","http://api.sr.se/api/rss/program/4916"); //
		 * "https://www.yahoo.com/news/rss/tech" //
		 * "http://www.nytimes.com/services/xml/rss/nyt/HomePage.xml");
		 * 
		 * // wrong feed manObj.subscribeFeed("news", //
		 * "http://sverigesradio.se/radioswedenpalattsvenska");
		 * 
		 * // to show all feeds added so far JSONArray jsarray = manObj.viewFeeds(); //
		 *
		 * 
		 * // to show the articles in a feed manObj.readFeed("news",
		 * "http://api.sr.se/api/rss/program/4916"); // grid
		 * 
		 * // to delete a existing feed manObj.removeFeed("news",
		 * "https://www.yahoo.com/news/rss/tech");
		 */

	}

	private void subscribeFeed() {

		System.out.println("Please enter category:");
		String category = scanner.nextLine().trim();
		System.out.println("Please enter feed url:");
		String url = scanner.nextLine().trim();

		try {

			if (manObj.subscribeFeed(category, url, inputUser)) {
				System.out.println("You have successfully subscribed to feed url: " + url);
			} else {
				System.out.println("Unable to subscribe to feed url: " + url);
			}

		} catch (FeedException | JSONFileException | IOException | RSSException e) {
			System.out.println(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	private void readFeed() {
		JSONArray arrFeed = null;
		try {

			arrFeed = manObj.viewFeeds();

		} catch (JSONFileException e) {
			System.out.println(e.getMessage());
		}
		if (arrFeed != null) {
			System.out.println("Following are the Feeds subscribed:");
			System.out.println("");

			boolean nofeed = true;
			Iterator<JSONObject> itarray = arrFeed.iterator();
			while (itarray.hasNext()) {
				JSONObject listObj = (JSONObject) itarray.next();
				if (listObj.get("username").equals(inputUser)) {
					System.out.println("Category: " + listObj.get("category").toString() + " | Url: "
							+ listObj.get("url").toString());
					nofeed = false;
				}
			}

			if (!nofeed) {
				System.out.println("");

				System.out.println("Please enter details to read articles from one of the feeds listed above.");
				System.out.println("Enter feed category :");
				String category = scanner.nextLine().trim();
				System.out.println("Enter feed url:");
				String url = scanner.nextLine().trim();

				try {
					List<String> articles = manObj.readFeed(category, url, inputUser);

					for (String message : articles) {
						System.out.println(message.toString());
					}

					System.out.println("");

				} catch (FeedException | JSONFileException | RSSException e) {
					System.out.println(e.getMessage());
				}
			}else
			{
				System.out.println("Empty feed list. No feeds subscribed yet !!");
				System.out.println("");
			}
		} else {
			System.out.println("No feeds subscribed.Please subscribe to read a feed.");
		}
	}

	private void unsubscribeFeed() {
		System.out.println("Please enter category:");
		String category = scanner.nextLine().trim();
		System.out.println("Please enter feed url:");
		String url = scanner.nextLine().trim();

		try {
			if (!manObj.removeFeed(category, url, inputUser)) {
				System.out.println("Error writing to file.");
			} else {
				System.out.println("Feed removed successfully. Url:" + url);
			}
		} catch (FeedException | JSONFileException e) {
			System.out.println(e.getMessage());
		}
	}

}
