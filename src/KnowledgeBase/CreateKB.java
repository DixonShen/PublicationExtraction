package KnowledgeBase;


import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * create the knowledge base
 * Created by Z003R98D on 2/24/2017.
 */
public class CreateKB {

    public Map<String, List<String>> mKB = new HashMap<>();

    private static final int RECORDS_COUNT = 10000;

    public static final String[] AttributeSet = {"title", "author", "sub_detail",
                                                 "pyear", "ppages", "pvolume"};

    /**
     * create the knowledge base
     * @return
     */
    public Map<String, List<String>> create() {
        System.out.println("Start initializing Knowledge Base");
        SQLUtils conn = new SQLUtils();
        Map<String, List<String>> mKB = new HashMap<>();
        for (String attr : AttributeSet) {
            String selectSQL = "select distinct " + attr + " from paper where ptag = 'article'";
            List<String> valueSet = new ArrayList<>();
            int count = 0;
            System.out.println("reading " + attr + " from database");
            ResultSet rs = conn.selectSQL(selectSQL);
            try {
                while (rs.next() && count < RECORDS_COUNT) {
                    String tempVal = rs.getString(1);
                    if (attr.equals("author")) {
                        tempVal = tempVal.replaceAll("_", " ");
                    }
                    System.out.println(tempVal);
                    valueSet.add(tempVal);
                    count++;
                    System.out.println(count);
                }
                if (attr.equals("title"))
                    mKB.put("title", valueSet);
                else if (attr.equals("author"))
                    mKB.put("author", valueSet);
                else if (attr.equals("sub_detail"))
                    mKB.put("journal", valueSet);
                else if (attr.equals("pyear"))
                    mKB.put("year", valueSet);
                else if (attr.equals("ppages"))
                    mKB.put("pages", valueSet);
                else if (attr.equals("pvolume"))
                    mKB.put("volume", valueSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Init KB successfully!");
        return mKB;
    }

    public void saveToFile() {
        Map<String, List<String>> kb = create();
        Iterator iterator = kb.keySet().iterator();
        try {
            File file = new File("knowledge_base_10000.txt");
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            while (iterator.hasNext()) {
                Object key = iterator.next();
                bw.write(key.toString());
                bw.newLine();
                List<String> list = kb.get(key);
                for (String s : list.toArray(new String[0])){
                    bw.write(s);
                    bw.newLine();
                }
            }
            bw.flush();
            bw.close();
            fw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

    public Map<String, List<String>> readKBFromFile(String path) {
        Map<String, List<String>> kb = new HashMap<>();
        File file = new File(path);
        try {
            InputStreamReader fileInputStream = new InputStreamReader(new FileInputStream(file));
            BufferedReader br = new BufferedReader(fileInputStream);
            String line;
            String key = "";
            boolean isStart = true;
            List<String> valSet = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                if (line.equals("title") || line.equals("author") || line.equals("journal") ||
                        line.equals("pages") || line.equals("year") || line.equals("volume")) {
                    if (!isStart){
                        List<String> tempValSet = new ArrayList<>(valSet);
                        kb.put(key, tempValSet);
                    }
                    else
                        isStart = false;
                    key = line;
                    valSet.clear();
                }
                else {
                    valSet.add(line);
                }
            }
            valSet.remove("\n");
//            System.out.println(key + ": " +valSet.size());
            kb.put(key, valSet);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return kb;
    }

    public static void main(String[] args) {
        CreateKB mKB = new CreateKB();
        Map<String, List<String>> kb = mKB.readKBFromFile("knowledge_base_10000.txt");
        for (String key : kb.keySet()) {
            int count = 0;
            System.out.println(key + ": " + kb.get(key).size());
            for (String s : kb.get(key).toArray(new String[0])){
                count++;
                if (count == 100) {
                    System.out.println(s);
                }
            }
        }
    }

}
