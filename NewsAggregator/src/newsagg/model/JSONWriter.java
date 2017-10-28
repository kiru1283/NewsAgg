package newsagg.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;


public class JSONWriter {

	private static String filename;
	// private static JSONArray urlList ;

	public JSONWriter() {
		// urlList = new JSONArray();
		filename = "test.json";
	}


	@SuppressWarnings("unchecked")
	public void jsonWrite(JSONArray jsonArray, String category, String url, boolean removeFeed) {

		try {
			if (!removeFeed) {
				
				//to subscribe a feed
				JSONObject obj = new JSONObject();

				obj.put("category", category);
				obj.put("url", url);

				// read JSON to check if already urls are added
				// JSONArray jsonArray = (new JSONReader().jsonReader());

				if (jsonArray == null) {

					jsonArray = new JSONArray();
				}

				jsonArray.add(obj);
				
			}
			
			
			FileWriter file = new FileWriter(filename);

			file.write(jsonArray.toJSONString());
			file.flush();
			file.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


}
