package Test;

import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dixonshen on 2017/3/28.
 */
public class prepareONDUXTestData {

    public static final String taggedFile = "src/Test/tagged_references.txt";

    public static void main(String[] args) {
        BufferedReader br;
        BufferedWriter bw;
        List<String> records = new ArrayList<>();
        int count = 0;
        Pattern p = Pattern.compile("<.{1,11}?>.+?</.{1,12}?>");
        try {
            br = new BufferedReader(new FileReader(taggedFile));
            String line;
            while ((line = br.readLine()) != null) {
                count++;
                String record = "";
                Matcher m = p.matcher(line);
                while (m.find()) {
                    String temp = m.group();
                    temp = temp.replaceAll("<.{1,12}>", "").trim();
                    record = record + temp + " ";
                    record = record.trim();
                }
                records.add(record);
            }
            System.out.println(records.size());
            System.out.println(count);
            bw = new BufferedWriter(new FileWriter("ONDUXTestData.txt"));
            for (String s : records) {
                bw.write(s);
                bw.write("\n");
            }
            bw.flush();
            bw.close();
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
