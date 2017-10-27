package newsagg.model;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;

public class JSONWriter {
	
	public void jsonwrite(){
	JSONObject obj = new JSONObject();
	
    obj.put("name", "mkyong.com");
    obj.put("age", new Integer(100));
    
    try (FileWriter file = new FileWriter("test.json")) {

        file.write(obj.toJSONString());
        file.flush();

    } catch (IOException e) {
        e.printStackTrace();
    }

    System.out.print(obj);
	}

}
