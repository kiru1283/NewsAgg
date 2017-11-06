package newsagg.model;

public class User {
	  	 private String userEncryptedPassword;
	     private String userSalt;
	     private String userName;
	     
	     
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getUserEncryptedPassword() {
			return userEncryptedPassword;
		}
		public void setUserEncryptedPassword(String userEncryptedPassword) {
			this.userEncryptedPassword = userEncryptedPassword;
		}
		public String getUserSalt() {
			return userSalt;
		}
		public void setUserSalt(String userSalt) {
			this.userSalt = userSalt;
		}
		

}
