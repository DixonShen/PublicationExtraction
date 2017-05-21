package Test;

import Block.Block;
import Block.MyBlock;
import Matching.Matching;
import Matching.Vocabulary;
import Reinforcement.LabelReinforcement;
import Reinforcement.PSModel;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static Matching.Matching.doMatching;
import static Matching.Matching.vocabularies;

/**
 * Created by dixonshen on 2017/3/28.
 */
public class testONDUX {

    public static void main(String[] args) throws Exception {
        Block block = new Block();
        Matching match = new Matching(block.getmKB());
        System.out.println("\n");

        String record1 = "Merged processes: a new condensed representation of Petri net behaviour. Victor_Khomenko,Alex_Kondratyev,Maciej_Koutny,Walter_Vogler， Acta Inf. 2006 43 307-330";
        String record2 = "Clustering Genes Using Heterogeneous Data Sources. Erliang_Zeng,Chengyong_Yang,Tao_Li,Giri_Narasimhan IJKDB 2010 1 12-28";
        String record3 = "Amjad Mehmood, Muhammad Muneer Umar, Houbing Song: ICMDS: Secure inter-cluster multiple-key distribution scheme for wireless sensor networks. Ad Hoc Networks 55: 97-106 (2017)";
        String record4 = "Jochen Neusser, Veronika Schleper: Numerical schemes for the coupling of compressible and incompressible fluids in several space dimensions. Applied Mathematics and Computation 304: 65-82 (2017)";

        List<String> records = new ArrayList<>();
        int count = 0;
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("temp_combined_data.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                line = line.split("\t")[1];
                records.add(line);
                count++;
//                if (count >= 5)
//                    break;
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }

        List<List<MyBlock>> recordList = new ArrayList<>();
        for (String record : records) {
            List<MyBlock> blocks = block.doBlock(record);
            doMatching(blocks, vocabularies);
            for (MyBlock block1 : blocks)
                System.out.println(block1.toString());
            recordList.add(blocks);
        }

        PSModel psm = new PSModel();
        List<List<MyBlock>> newList = psm.PrepareBlocks(recordList, true);
//        newList.addAll(recordList);
        recordList = psm.PrepareBlocks(recordList, false);
        System.out.println();

        psm.InitModel();
        psm.CreateModel(newList);

        LabelReinforcement.Reinforcement(recordList, vocabularies);
        System.out.println("Final Result");
        FileWriter fw = new FileWriter("ONDUX_result.txt");
        for (List<MyBlock> record : recordList) {
            for (MyBlock block2 : record) {
                String label = block2.getLabel();
                String[] contents = block2.getContents().split(" ");
                for (String word : contents) {
                    fw.write(word + " " + label + "\n");
                }
            }
            fw.write("\n");
        }
        fw.close();
    }
}
