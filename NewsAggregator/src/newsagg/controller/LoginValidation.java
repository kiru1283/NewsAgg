package newsagg.controller;

import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import newsagg.model.JSONReader;
import newsagg.model.JSONWriter;
import newsagg.model.User;
import newsagg.model.UserInfo;

public class LoginValidation {
	private static final String userfilename = "users.json";
	private JSONArray arrUser;
	private String prevSalt;
	private String prevPwd;

	public boolean validateUser(String userid, String pwd) {

		UserInfo userObj = new UserInfo();
		boolean validUser = false;

		boolean userExists = checkUser(userid);

		if (userExists) {
			validUser = userObj.authenticateUser(userid, pwd, prevSalt, prevPwd);
		}

		return validUser;

	}

	public boolean createUser(String userid, String pwd) {

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
			System.out.println("User Already Exists");
			retVal = false;
		}

		return retVal;

	}

	@SuppressWarnings("unchecked")
	private boolean checkUser(String userid) {

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
