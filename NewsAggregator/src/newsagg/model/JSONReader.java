package newsagg.model;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JSONReader {

	private static String filename;
	
	public JSONReader(String filename) {
		
		//filename = "test.json";
		this.filename = filename;
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
