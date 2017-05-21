package DataPreprocess;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dixonshen on 2017/1/12.
 */
public class PrepareTrainingData {

    public static final String taggedFile = "";
    private static String LINE_LOCATION = "";

    public static String GetLabel(String s) {
        char[] chs = s.toCharArray();
        String label = "";
        for (char ch : chs) {
            if (ch == '<') continue;
            if (ch == '>') break;
            label += ch;
        }
        return label.toUpperCase();
    }


    /**
     * 将test_data.xml文件处理为CRF所需的训练数据
     * @param filePath file path
     */
    public static void prepareCRFTestData(String filePath) throws Exception {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(new File(filePath));
        Element root = document.getRootElement();
        System.out.println("Root: " + root.getName());
        List<Element> articles = root.elements();
        System.out.println("Total number of articles: " + articles.size());
        FileWriter fw = new FileWriter("crf_training_data_2.txt");
        for (Element article : articles) {
            List<Element> attrs = article.elements();
            int labelCount = 1;
            for (Element attr : attrs) {
                if (labelCount == 1)
                    LINE_LOCATION = "RECORD_START";
                else if (labelCount == attrs.size())
                    LINE_LOCATION = "RECORD_END";
                else LINE_LOCATION = "RECORD_MIDDLE";
                labelCount++;
                String label = attr.getName();
                if (attr.getName().equals("record_ID")) continue;
                System.out.println(attr.getText().replaceAll("[`~!@#$^&*=|{}:;,\\\\[\\\\].<>/?！￥…（）—_【】‘；：”“。，、？]", ""));
                String[] contents = attr.getText().replaceAll("[`~!@#$^&*=|{}:;,\\\\[\\\\].<>/?！￥…（）—_【】‘；：”“。，、？]", "").split(" +");
                for (String word : contents) {
                    String features = ExtractFeatures.GetFeatures(word);
                    String line = word + " "  + features  + " "  + LINE_LOCATION + " " + label;
//                    System.out.println(line);
                    fw.write(line + "\n");
                }
            }
            fw.write("\n");
        }
        fw.close();
    }

    public static void prepareCRFTestData2(String path) {
        BufferedReader br;
        int count = 0;
        List<String> trainingData = new ArrayList<>();
//        List<String> testingData = new ArrayList<>();
        Pattern p = Pattern.compile("<.{1,11}?>.+?</.{1,12}?>");
        try {
            br = new BufferedReader(new FileReader(taggedFile));
            String line = "";
            while((line = br.readLine()) != null) {
                count++;
                LINE_LOCATION = "LINE_START";
//                System.out.println(count);
//                System.out.println(line);
                List<String> list = new ArrayList<>();
                Matcher m = p.matcher(line);
                while (m.find()){
                    list.add(m.group());
//                    System.out.println(m.group());
                }
                String label = "";
                for (int i = 0; i < list.size(); i++) {
                    String temp = list.get(i);
                    label = GetLabel(temp);
                    temp = temp.replaceAll("<.{1,12}>", "").trim();
//                    System.out.println(temp);
                    String[] infos = temp.split(" +");
                    for ( int j = 0; j < infos.length; j++) {
                        String oneLine = "";
                        if (i == list.size()-1 && j == infos.length-1)
                            LINE_LOCATION = "LINE_END";
                        if (j == 0) {
                            oneLine = infos[0]+ " " + ExtractFeatures.GetFeatures(infos[0]) + " " + LINE_LOCATION + " B-" + label;
                        } else {
                            oneLine = infos[j]+ " " + ExtractFeatures.GetFeatures(infos[j]) + " " + LINE_LOCATION + " I-" + label;
                        }
//                        System.out.println(oneLine);
//                        if (count <= 350)
                        trainingData.add(oneLine);
//                        if (count > 350) testingData.add(oneLine);
                        LINE_LOCATION = "LINE_IN";
                    }
                }
//                if (count <= 350)
                trainingData.add("\n");
//                else testingData.add("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            FileWriter fw1 = new FileWriter("ondux_training_data.txt");
//            FileWriter fw2 = new FileWriter("crf_test_data.txt");
            int train_count = 1;
            int test_count = 1;
            for (String s : trainingData.toArray(new String[0])) {
                System.out.println(train_count + ": " + s);
                fw1.write(s);
                if (s != "\n")
                    fw1.write("\n");
                if (s == "\n") train_count++;
            }

//            for (String s : testingData.toArray(new String[0])) {
//                System.out.println(test_count + ": " + s);
//                fw2.write(s);
//                if (s != "\n")
//                    fw2.write("\n");
//                if (s == "\n") test_count++;
//            }
            fw1.close();
//            fw2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String s : trainingData.toArray(new String[0])) {
            if (s != "\n") {
                String[] temp = s.split(" ");
                System.out.println(temp[temp.length-1]);
            }
            else System.out.println("\n");
        }

        System.out.println("total items: " + count);
    }

    public static void main(String[] args) throws Exception {
        prepareCRFTestData("test_data.xml");
    }
}

