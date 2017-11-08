package newsagg.controller;

import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import newsagg.exceptions.JSONFileException;
import newsagg.model.JSONReader;
import newsagg.model.JSONWriter;

public class MarkArticle {

	private String favfilename = "favourites.json";

	@SuppressWarnings("rawtypes")
	public JSONArray saveArticle(String category, String url, String inputUser, boolean remove) throws JSONFileException {

		
		boolean feedexists = false;
		JSONReader readObj = new JSONReader(favfilename);

		JSONArray arrFeed = readObj.jsonReader();

		if (arrFeed != null) {

			if (remove) {
				Iterator itarray = arrFeed.iterator();

				while (!feedexists && itarray.hasNext()) {

					JSONObject listObj = (JSONObject) itarray.next();

					if (listObj.get("category").equals(category) && listObj.get("url").equals(url)
							&& listObj.get("username").equals(inputUser)) {

						itarray.remove();

						feedexists = true;
					}
				}
			}

			JSONWriter jsonObj = new JSONWriter(favfilename);
			 jsonObj.jsonWrite(arrFeed, category, url, remove, inputUser);
		}
		return arrFeed;

	}

	@SuppressWarnings("unchecked")
	public JSONArray viewFavourites(String inputUser) throws JSONFileException {
		JSONReader readObj = new JSONReader(favfilename);

		JSONArray arrFeed = readObj.jsonReader();

		if (arrFeed != null) {
			Iterator<JSONObject> itarray = arrFeed.iterator();

			while (itarray.hasNext()) {
				JSONObject listObj = (JSONObject) itarray.next();
				if (!listObj.get("username").equals(inputUser)) {
					itarray.remove();
				}
			}
		}
		return arrFeed;
	}

}
