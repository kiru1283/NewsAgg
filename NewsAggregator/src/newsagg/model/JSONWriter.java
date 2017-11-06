package newsagg.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import newsagg.exceptions.JSONFileException;

import java.io.FileWriter;
import java.io.IOException;

public class JSONWriter {

	private String filename;

	public JSONWriter(String filename) {
		this.filename = filename;

	}

	@SuppressWarnings("unchecked")
	public boolean jsonWrite(JSONArray jsonArray, String category, String url, boolean removeFeed, String username) throws JSONFileException {
		
		boolean retVal = false;		
		
		if (!removeFeed) {
			// to subscribe a feed
			JSONObject obj = new JSONObject();
			obj.put("username", username);
			obj.put("category", category);
			obj.put("url", url);
			

			if (jsonArray == null) {

				jsonArray = new JSONArray();
			}

			jsonArray.add(obj);
		}
		
		retVal =fileWriter(jsonArray);
		
		return retVal;

	}

	@SuppressWarnings("unchecked")
	public boolean jsonUserWrite(JSONArray jsonArray, String userid, String salt, String pwd) throws JSONFileException {

		boolean retVal = false;
		
		JSONObject obj = new JSONObject();

		obj.put("userid", userid);
		obj.put("salt", salt);
		obj.put("pwd", pwd);

		if (jsonArray == null) {
			jsonArray = new JSONArray();
		}

		jsonArray.add(obj);
		
		retVal = fileWriter(jsonArray);

		return retVal;
	}

	private boolean fileWriter(JSONArray jsonArray) throws JSONFileException {
		
		boolean retVal = false;
		
		try {

			FileWriter file = new FileWriter(filename);

			file.write(jsonArray.toJSONString());
			file.flush();
			file.close();
			
			retVal = true;
			
		} catch (IOException  e) {
			
			//System.out.println(e.getMessage());
			throw new JSONFileException("Error Writing to file "+filename+". Error description: "+e.getMessage());
		}
		
		return retVal;
	}

}
