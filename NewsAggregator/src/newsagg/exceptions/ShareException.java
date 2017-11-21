package newsagg.exceptions;

/**
 * Class to capture exceptions that happen during email of articles
 * @author Kiruthiga
 *
 */

public class ShareException extends Exception {

	/**
	 * Constructor method to pass the custom exception to super class
	 * @param message
	 */
	public ShareException (String message) {
		super(message);
	}


}
