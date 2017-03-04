package Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jdk.nashorn.internal.parser.JSONParser;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * Created by Z003R98D on 2/27/2017.
 */
public class testJSON {

    public static void main(String[] args) {
        try {
            String testJson = "";
            Gson gson = new Gson();
            File file = new File("./src/Test/json1.txt");
            InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
            BufferedReader br = new BufferedReader(reader);
            String line = "";
            while ((line = br.readLine()) != null) {
                testJson += line;
            }
            System.out.println(testJson);
            Map<String, Object> result = new Gson().fromJson(testJson, new TypeToken<Map<String, Object>>(){}.getType());
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
