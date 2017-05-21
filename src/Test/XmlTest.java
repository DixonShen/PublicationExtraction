package Test;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

/**
 * Created by dixonshen on 2017/5/18.
 */
public class XmlTest {

    public static void main(String[] args) throws Exception {
        String filePath = "test_data.xml";
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(new File(filePath));
        Element root = document.getRootElement();
        System.out.println("Root: " + root.getName());
        List<Element> articles = root.elements();
        System.out.println("Total number of articles: " + articles.size());
        FileWriter fw = new FileWriter("ONDUX_real_result.txt");
        for (Element article : articles) {
            List<Element> attrs = article.elements();
            for (Element attr : attrs) {
                String label = attr.getName();
                if (attr.getName().equals("record_ID")) continue;
                String[] contents = attr.getText().replaceAll("[`~!@#$^&*=|{}:;,\\\\[\\\\].<>/?！￥…（）—_【】‘；：”“。，、？]", "").split(" +");
                for (String word : contents) {
                    String line = word + " " + label;
                    System.out.println(line);
                    fw.write(line + "\n");
                }
            }
            fw.write("\n");
        }
        fw.close();
    }
}
