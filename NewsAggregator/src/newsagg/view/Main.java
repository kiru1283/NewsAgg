package newsagg.view;

import java.util.Scanner;

import newsagg.controller.LoginValidation;
import newsagg.controller.ManageFeed;

public class Main {

	/**
	 * @param args
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome to the NewsBoard Application !!");

		boolean validUser = false;
		LoginValidation loginObj = new LoginValidation();

		while (!validUser) {

			System.out.println("To Start Application Enter: CreateUser or Login");
			String inputOption = scanner.nextLine();

			if (inputOption.toUpperCase().trim().equals("CREATEUSER")) {

				System.out.println("Please enter username:");
				String inputUser = scanner.nextLine();
				System.out.println("Please enter password:");
				String inputPass = scanner.nextLine();

				// for new users
				if (loginObj.createUser(inputUser, inputPass)) {
					System.out.println("User account created successfully!!");
					validUser = true;
				} else {
					System.out.println("Error while creating User account!!");
				}

			} else if (inputOption.toUpperCase().trim().equals("LOGIN")) {

				System.out.println("Please enter username:");
				String inputUser = scanner.nextLine();
				System.out.println("Please enter password:");
				String inputPass = scanner.nextLine();

				if (loginObj.validateUser(inputUser, inputPass))

				{
					System.out.println("Logged in!");
					validUser = true;

				} else {
					System.out.println("Incorrect username/password !!");

				}

			} else {
				System.out.println("Invalid Option Entry!!");
			}
		}
		// for existsing users
		if (validUser)

		{
			// System.out.println("Logged in!");
			String loop = "GO";
			ManageFeed manObj = new ManageFeed();

			while (!loop.equals("EXIT")) {

				System.out.println("Please Enter Any One Option: ");
				System.out.println("Subscribe Read UnSubscribe ViewAll Exit");

				String inputVal = scanner.nextLine();

				if (inputVal.toUpperCase().trim().equals("SUBSCRIBE")) {
					System.out.println("Please enter category:");
					String category = scanner.nextLine();
					System.out.println("Please enter feed url:");
					String url = scanner.nextLine();
					
					manObj.subscribeFeed(category, url);
					
				} else if (inputVal.toUpperCase().trim().equals("READ")) {
					System.out.println("Please enter category:");
					String category = scanner.nextLine();
					System.out.println("Please enter feed url:");
					String url = scanner.nextLine();
					
					manObj.readFeed(category, url);
					
				} else if (inputVal.toUpperCase().trim().equals("UNSUBSCRIBE")) {
					System.out.println("Please enter category:");
					String category = scanner.nextLine();
					System.out.println("Please enter feed url:");
					String url = scanner.nextLine();
					
					manObj.removeFeed(category, url);
					
				} else if (inputVal.toUpperCase().trim().equals("VIEWALL")) {
				
					manObj.viewFeeds(true);
			
				} else if (inputVal.toUpperCase().trim().equals("EXIT")) {
					
					loop = "EXIT";
				
				} else {
					System.out.println("Invalid Option Entry!!");
				}

			}

			System.out.println("Exited NewsBoard Application!!");

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
			 * TODO: bind values of jsonarray to tree view
			 * 
			 * // to show the articles in a feed manObj.readFeed("news",
			 * "http://api.sr.se/api/rss/program/4916"); // TODO: bind values of articles to
			 * grid
			 * 
			 * // to delete a existing feed manObj.removeFeed("news",
			 * "https://www.yahoo.com/news/rss/tech");
			 */
		}
	}

}
