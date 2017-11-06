package newsagg.controller;

import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import newsagg.exceptions.AuthenticationException;
import newsagg.exceptions.JSONFileException;
import newsagg.model.JSONReader;
import newsagg.model.JSONWriter;
import newsagg.model.User;
import newsagg.model.UserInfo;
/**
 * 
 * @author Kiruthiga
 * @category Controller class for User account maintenance
 */
public class LoginValidation {
	private static final String userfilename = "users.json";
	private JSONArray arrUser;
	private String prevSalt;
	private String prevPwd;

	/**
	 * Method to validate the credentials of user from the values saved in users.json
	 * @param userid - input userid to login
	 * @param pwd - input password to login
	 * @return - returns true if the input userid and password match the values in users.json
	 * @throws AuthenticationException - when password encryption fails
	 * @throws JSONFileException - when reading values from users.json fails
	 */
	public boolean validateUser(String userid, String pwd) throws AuthenticationException,JSONFileException {

		UserInfo userObj = new UserInfo();
		boolean validUser = false;

		boolean userExists = checkUser(userid);

		if (userExists) {
			validUser = userObj.authenticateUser(userid, pwd, prevSalt, prevPwd);
		}

		return validUser;

	}

	/**
	 * Method to stored userid and password of new user in users.json
	 * @param userid - input userid to create new user
	 * @param pwd - input password to create new user
	 * @return - returns true if the userid and encrypted password are saved in users.json
	 * @throws AuthenticationException - when password encryption fails 
	 * @throws JSONFileException -  when writing values to users.json fails
	 */
	public boolean createUser(String userid, String pwd) throws AuthenticationException,JSONFileException  {

		boolean retVal = true;

		UserInfo userObj = new UserInfo();

		boolean userExists = checkUser(userid);

		if (!userExists) {
			Map<String, User> newUser = userObj.signUp(userid, pwd);

			if (newUser != null) {

				JSONWriter jsonObj = new JSONWriter(userfilename);
				jsonObj.jsonUserWrite(arrUser, userid, newUser.get(userid).getUserSalt(),
						newUser.get(userid).getUserEncryptedPassword());

			}
		} else {
			throw new AuthenticationException("UserID Already Exists. Please create user with a different UserID.");
			
		}

		return retVal;

	}

	//Method to check is user exists in users.json and fetch the previous salt and password 
	@SuppressWarnings("unchecked")
	private boolean checkUser(String userid) throws JSONFileException {

		boolean userExists = false;

		JSONReader readObj = new JSONReader(userfilename);
		arrUser = readObj.jsonReader();

		if (arrUser != null) {
			Iterator<JSONObject> it = arrUser.iterator();

			while (!userExists && it.hasNext()) {
				JSONObject jsObj = it.next();
				if (jsObj.get("userid").equals(userid)) {
					userExists = true;
					prevSalt=jsObj.get("salt").toString();
					prevPwd = jsObj.get("pwd").toString();
							
				}
			}
		}

		return userExists;

	}

}
