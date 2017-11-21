package newsagg.model;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import newsagg.exceptions.JSONFileException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class used for reading user and feed Json files
 * @author Kiruthiga
 *
 */
public class JSONReader {

	private  String filename;
	
	/**
	 * Constructor of JSONReader to set the file which will be read
	 * @param filename - name of file to read
	 */
	public JSONReader(String filename) {
		
		this.filename = filename;
	}

	/**
	 * Method to read JSON file
	 * @return - array of elements read from Json file
	 * @throws JSONFileException - when there is an error reading json file
	 */
	public JSONArray jsonReader() throws JSONFileException {

		JSONParser parser = new JSONParser();
		JSONArray jsonArray = null;
		try {
			
			File file = new File(filename);
			FileReader filereader = new FileReader(file);
			
			if (file.length() != 0) {
				jsonArray = (JSONArray) parser.parse(filereader);
			}else
			{
				jsonArray = new JSONArray();

			}			

			filereader.close();

		} catch ( IOException | ParseException e) {
			
			throw new JSONFileException("Error Reading JSON file "+filename+". Error description :"+e.getMessage());
			
		}
		return jsonArray;
	}

}
