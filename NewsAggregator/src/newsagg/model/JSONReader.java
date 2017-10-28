package newsagg.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JSONReader {

	private static String filename;
	
	public JSONReader() {
		
		filename = "test.json";
	}

	public void jsonReaderMessage() {

		JSONParser parser = new JSONParser();

		try {

			JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(filename));
			System.out.println(jsonArray);

			for (Object jsonObj : jsonArray) {

				JSONObject message = (JSONObject) jsonObj;

				// String articleid = (String) message.get("articleid");
				// System.out.println(articleid);

				String title = (String) message.get("title");
				System.out.println(title);

				String description = (String) message.get("description");
				System.out.println(description);

				String link = (String) message.get("link");
				System.out.println(link);

				String guid = (String) message.get("guid");
				System.out.println(guid);

				String creator = (String) message.get("creator");
				System.out.println(creator);

				String pubdate = (String) message.get("pubdate");
				System.out.println(pubdate);

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	public JSONArray jsonReader() {

		JSONParser parser = new JSONParser();
		JSONArray jsonArray = null;
		try {
			
			File file = new File(filename);
			FileReader filereader = new FileReader(file);
			
			if (file.length() != 0) {
				jsonArray = (JSONArray) parser.parse(filereader);
			}			

			filereader.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}  catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return jsonArray;
	}

}
