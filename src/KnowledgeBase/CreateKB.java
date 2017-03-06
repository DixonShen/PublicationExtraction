package KnowledgeBase;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static void main(String[] args) {
        String s = "A 3D face animation system for mobile devices.";
        System.out.println(s.contains("devices"));
    }

}
