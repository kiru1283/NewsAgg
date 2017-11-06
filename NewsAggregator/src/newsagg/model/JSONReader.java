package newsagg.model;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import newsagg.exceptions.JSONFileException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class JSONReader {

	private  String filename;
	
	public JSONReader(String filename) {
		
		//filename = "test.json";
		this.filename = filename;
	}

	public JSONArray jsonReader() throws JSONFileException {

		JSONParser parser = new JSONParser();
		JSONArray jsonArray = null;
		try {
			
			File file = new File(filename);
			FileReader filereader = new FileReader(file);
			
			if (file.length() != 0) {
				jsonArray = (JSONArray) parser.parse(filereader);
			}			

			filereader.close();

		} catch ( IOException | ParseException e) {
			//e.printStackTrace();
			throw new JSONFileException("Error Reading JSON file "+filename+". Error description :"+e.getMessage());
			
		}
		return jsonArray;
	}

}
