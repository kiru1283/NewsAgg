package newsagg.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class JSONWriter {

	private static String filename;

	public JSONWriter(String filename) {
		this.filename = filename;

	}

	@SuppressWarnings("unchecked")
	public boolean jsonWrite(JSONArray jsonArray, String category, String url, boolean removeFeed) {

		if (!removeFeed) {
			// to subscribe a feed
			JSONObject obj = new JSONObject();

			obj.put("category", category);
			obj.put("url", url);

			if (jsonArray == null) {

				jsonArray = new JSONArray();
			}

			jsonArray.add(obj);
		}
		boolean retVal =fileWriter(jsonArray);
		return retVal;

	}

	@SuppressWarnings("unchecked")
	public boolean jsonUserWrite(JSONArray jsonArray, String userid, String salt, String pwd) {

		JSONObject obj = new JSONObject();

		obj.put("userid", userid);
		obj.put("salt", salt);
		obj.put("pwd", pwd);

		if (jsonArray == null) {
			jsonArray = new JSONArray();
		}

		jsonArray.add(obj);
		boolean retVal = fileWriter(jsonArray);

		return retVal;
	}

	private boolean fileWriter(JSONArray jsonArray) {
		boolean retVal = true;
		try {

			FileWriter file = new FileWriter(filename);

			file.write(jsonArray.toJSONString());
			file.flush();
			file.close();
		} catch (FileNotFoundException e) {
			retVal = false;
			System.out.println(e.getMessage());
		} catch (IOException e) {
			retVal = false;
			System.out.println(e.getMessage());
		}
		
		return retVal;
	}

}
