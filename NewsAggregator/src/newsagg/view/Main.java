package newsagg.view;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import newsagg.controller.LoginValidation;
import newsagg.controller.ManageFeed;
import newsagg.controller.MarkArticle;
import newsagg.controller.ShareArticle;
import newsagg.controller.ViewArticle;
import newsagg.exceptions.ArticleException;
import newsagg.exceptions.AuthenticationException;
import newsagg.exceptions.FeedException;
import newsagg.exceptions.JSONFileException;
import newsagg.exceptions.RSSException;
import newsagg.exceptions.ShareException;

/**
 * 
 * @author Kiruthiga
 * @category Main Class acting as view
 * 
 */
public class Main {
	private Scanner scanner;
	private UserLogin userObj;
	private FeedView feedObj;
	private ArticleView artObj;
	private String inputUser;
	

	/**
	 * constructor to initialize the scanner and controller classes
	 */
	public Main() {
		scanner = new Scanner(System.in);
		userObj = new UserLogin();
		feedObj = new FeedView();
		artObj = new ArticleView();
		
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

		System.out.println("***********************************");
		System.out.println("* Welcome To FeedBook Application *");
		System.out.println("***********************************");
		System.out.println("");

		while (true) {

			boolean validUser = false;

			while (!validUser) {

				System.out.println("To Enter Please Select One Option : ");
				System.out.println("C -> Create New User Account");
				System.out.println("L -> Login With Existing User Account");

				String inputOption = scanner.nextLine();

				if (inputOption.toUpperCase().trim().equals("C")) {

					validUser = userObj.createNewUser();

				} else if (inputOption.toUpperCase().trim().equals("L")) {

					validUser = userObj.loginUser();

				} else {
					System.out.println("Invalid Option Entered!!");
				}

			}

			// for Valid users choose operation
			if (validUser) {
				inputUser = userObj.getinputUser();
				userOperations();
			}
		}
	}
	

	// Method used for processing user input after login
	private void userOperations() {

		String loop = "GO";

		while (!loop.equals("LOGOUT")) {

			System.out.println("--------------");
			System.out.println("| MAIN MENU |");
			System.out.println("--------------");
			System.out.println("Please Enter An Option: ");
			System.out.println("S -> Subscribe a feed");
			System.out.println("R -> Read a feed");
			System.out.println("V -> View Favourite Articles");
			System.out.println("U -> UnSubscribe a feed");
			System.out.println("X -> Exit the application");

			String inputVal = scanner.nextLine();

			if (inputVal.toUpperCase().trim().equals("S")) {

				feedObj.subscribeFeed(inputUser);

			} else if (inputVal.toUpperCase().trim().equals("R")) {

				feedObj.readFeed(inputUser);

			} else if (inputVal.toUpperCase().trim().equals("U")) {

				feedObj.unsubscribeFeed(inputUser);
			} else if (inputVal.toUpperCase().trim().equals("V")) {

				artObj.viewFavourites(inputUser);

			} else if (inputVal.toUpperCase().trim().equals("X")) {

				loop = "LOGOUT";

			} else {
				System.out.println("Invalid Option Entered!!");
			}

		}
		System.out.println("**************************************");
		System.out.println("*Logged Out Of FeedBook Application!!*");
		System.out.println("**************************************");
		System.out.println("");

	}

	

}
