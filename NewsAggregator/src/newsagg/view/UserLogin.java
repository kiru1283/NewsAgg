package newsagg.view;

import java.util.Scanner;

import newsagg.controller.LoginValidation;
import newsagg.exceptions.AuthenticationException;
import newsagg.exceptions.JSONFileException;

/**
 * Class to provide User login functionality
 * 
 * @author Kiruthiga
 * 
 */
public class UserLogin {
	private Scanner scanner;
	private LoginValidation loginObj;
	private String inputUser;

	/**
	 * Constructor method to initialize the controller
	 */
	public UserLogin() {
		scanner = new Scanner(System.in);
		loginObj = new LoginValidation();

	}

	/**
	 * Method to return the username of logged in user
	 * 
	 * @return - logged in username
	 */
	public String getinputUser() {
		return inputUser;
	}

	/**
	 * Method to create new User account
	 * @return true if account was created successfully
	 */
	public boolean createNewUser() {

		System.out.println("Please Enter Username:");
		inputUser = scanner.nextLine().trim();

		System.out.println("Please Enter Password:");
		String inputPass = scanner.nextLine().trim();

		boolean validUser = false;

		if (inputUser.equals("")) {
			System.out.println("Blank Username is not allowed !!");
			System.out.println("");
		}
		if (inputPass.equals("")) {
			System.out.println("Blank Password is not allowed !!");
			System.out.println("");
		}

		if (!inputUser.equals("") && !inputPass.equals("")) {
			// for new users
			try {
				if (loginObj.createUser(inputUser, inputPass)) {
					System.out.println("User account created successfully!!");
					System.out.println("");
					validUser = true;
				} else {
					System.out.println("Error while creating User account!!");
					System.out.println("");
				}
			} catch (AuthenticationException | JSONFileException e) {
				System.out.println(e.getMessage());
			}
		}

		return validUser;
	}

	/**
	 * Method to validate the userid and password of an existing user account
	 * @return true if the userid and password match the values in the JSON
	 */
	public boolean loginUser() {

		boolean validUser = false;
		System.out.println("Please Enter Username:");
		inputUser = scanner.nextLine().trim();
		System.out.println("Please Enter Password:");
		String inputPass = scanner.nextLine().trim();

		if (inputUser.equals("")) {
			System.out.println("Blank Username is not allowed !!");
			System.out.println("");
		}
		if (inputPass.equals("")) {
			System.out.println("Blank Password is not allowed !!");
			System.out.println("");
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
					System.out.println("Incorrect Username or Password !!");
					System.out.println("");

				}
			} catch (AuthenticationException | JSONFileException e) {
				System.out.println(e.getMessage());
			}
		}

		return validUser;
	}

}
