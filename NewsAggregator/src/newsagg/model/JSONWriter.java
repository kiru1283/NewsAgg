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
	public void jsonWriteMessage(Feed feed) {

		JSONArray messageList = new JSONArray();

		for (Article message : feed.getArticles()) {

			JSONObject obj = new JSONObject();

			obj.put("articleid", message.getArticleid());
			obj.put("title", message.getTitle());
			obj.put("description", message.getDescription());
			obj.put("link", message.getLink());
			obj.put("creator", message.getCreator());
			obj.put("guid", message.getGuid());
			obj.put("pubdate", message.getPubdate());

			messageList.add(obj);

		}
		try (FileWriter file = new FileWriter(filename)) {

			file.write(messageList.toJSONString());
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}

		// System.out.print(obj);
	}

	@SuppressWarnings("unchecked")
	public void jsonWrite(String category, String url) {

		try {
			JSONObject obj = new JSONObject();

			obj.put("category", category);
			obj.put("url", url);
			
			//read JSON to check if already urls are added
			JSONArray jsonArray = (new JSONReader().jsonReader());
			
			if (jsonArray == null){
				
				jsonArray = new JSONArray();
			}
		
			jsonArray.add(obj);
			
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
