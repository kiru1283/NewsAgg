package newsagg.model;

import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import newsagg.exceptions.AuthenticationException;


public class UserInfo {

	private Map<String, User> userDatabase = new HashMap<String, User>();

	public Map<String, User> signUp(String userName, String password) throws AuthenticationException {
		
		try {
			String salt = getNewSalt();
			String encryptedPassword = getEncryptedPassword(password, salt);
			User user = new User();
			user.setUserEncryptedPassword(encryptedPassword);
			user.setUserName(userName);
			user.setUserSalt(salt);
			saveUser(user);
		} catch (Exception e) {
			throw new AuthenticationException("An exception occured while creating user account.");
			
		}
		return userDatabase;

	}

	// Returns base64 encoded salt
	private String getNewSalt() throws Exception {
		
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[8];
		random.nextBytes(salt);
		return Base64.getEncoder().encodeToString(salt);
	}

	private void saveUser(User user) {
		userDatabase.put(user.getUserName(), user);		

	}

	// Get a encrypted password using PBKDF2 hash algorithm
	private String getEncryptedPassword(String password, String salt) throws Exception {
		String algorithm = "PBKDF2WithHmacSHA1";
		int derivedKeyLength = 160; // for SHA1
		int iterations = 20000; 
		byte[] saltBytes = Base64.getDecoder().decode(salt);

		KeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, iterations, derivedKeyLength);
		SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);
		byte[] encBytes = f.generateSecret(spec).getEncoded();

		return Base64.getEncoder().encodeToString(encBytes);

	}

	public boolean authenticateUser(String inputUser, String inputPass, String prevSalt, String prevPwd) throws AuthenticationException  {
		
		boolean retValue = false;
			try {
				
				String calculatedHash = getEncryptedPassword(inputPass, prevSalt);
				if(calculatedHash.equals(prevPwd)) {
					retValue = true;
				}
			} catch (Exception e) {
				
				throw new AuthenticationException("An exception occured while authenticating user.");
			}
			return retValue;

	}

}
