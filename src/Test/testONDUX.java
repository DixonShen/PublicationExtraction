package Test;

import Block.Block;
import Block.MyBlock;
import Matching.Matching;
import Matching.Vocabulary;
import Reinforcement.LabelReinforcement;
import Reinforcement.PSModel;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static Matching.Matching.doMatching;
import static Matching.Matching.vocabularies;

/**
 * Created by dixonshen on 2017/3/28.
 */
public class testONDUX {

    public static void main(String[] args) {
        Block block = new Block();
        Matching match = new Matching(block.getmKB());
        System.out.println("\n");

        String record1 = "Merged processes: a new condensed representation of Petri net behaviour. Victor_Khomenko,Alex_Kondratyev,Maciej_Koutny,Walter_Vogler， Acta Inf. 2006 43 307-330";
        String record2 = "Clustering Genes Using Heterogeneous Data Sources. Erliang_Zeng,Chengyong_Yang,Tao_Li,Giri_Narasimhan IJKDB 2010 1 12-28";
        String record3 = "Amjad Mehmood, Muhammad Muneer Umar, Houbing Song: ICMDS: Secure inter-cluster multiple-key distribution scheme for wireless sensor networks. Ad Hoc Networks 55: 97-106 (2017)";
        String record4 = "Jochen Neusser, Veronika Schleper: Numerical schemes for the coupling of compressible and incompressible fluids in several space dimensions. Applied Mathematics and Computation 304: 65-82 (2017)";

        List<String> records = new ArrayList<>();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("500.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                records.add(line);
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }

        List<List<MyBlock>> recordList = new ArrayList<>();
        for (String record : records) {
            List<MyBlock> blocks = block.doBlock(record);
            doMatching(blocks, vocabularies);
            recordList.add(blocks);
        }

        PSModel psm = new PSModel();
        List<List<MyBlock>> newList = psm.PrepareBlocks(recordList, true);
        psm.InitModel();
        psm.CreateModel(newList);
        recordList = psm.PrepareBlocks(recordList, false);
        LabelReinforcement.Reinforcement(recordList, vocabularies);
        for (List<MyBlock> record : recordList) {
            for (MyBlock blcok : record) {
                System.out.println(block.toString() + " ");
            }
            System.out.println("\n");
        }

    }
}
