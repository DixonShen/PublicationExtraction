package Block;

import KnowledgeBase.CreateKB;

import java.util.*;

/**
 * Created by dixonshen on 2017/2/28.
 */
public class Block {

    private Map<String, List<String>> mKB = new HashMap<>();

    private boolean isFound = false;

    public Block() {
        this.mKB = new CreateKB().readKBFromFile("knowledge_base_10000.txt");
        int i = 0;
        for (List<String> list : mKB.values())
            for (String s : list.toArray(new String[0]))
                i++;

    }

    public Map<String, List<String>> getmKB(){
        return mKB;
    }

    /**
     *
     * @param record
     * @return
     */
    public List<MyBlock> doBlock(String record) {
        record = record.replaceAll("[`~!@#$^&*()=|{}:;,\\\\[\\\\].<>/?！￥…（）—_【】‘；：”“。，、？]", " ");
        System.out.println(record);
        System.out.println("start blocking step!");
        List<MyBlock> result = new ArrayList<>();
        String[] terms = record.split(" +");
        for (String s : terms) {
            System.out.print(s + " ");
        }
        System.out.println();
        System.out.println(terms.length);
        MyBlock[] myBlock = new MyBlock[terms.length];
        int index = 0;
        myBlock[0] = new MyBlock(terms[0]);
        for (int i=1; i<terms.length; i++) {
            isFound = false;
            OUT:
            for (List<String> valueSet : mKB.values()) {
                for (String s : valueSet.toArray(new String[0])) {
                    if (isContains(s, terms[i-1], terms[i])) {
                        System.out.println(terms[i-1] + ", " + terms[i] + ": " + s);
                        myBlock[index].addOneString(terms[i]);
                        isFound = true;
                        break OUT;
                    }
                }
            }
            if (!isFound) {
                index++;
                myBlock[index] = new MyBlock(terms[i]);
            }
        }
        for (int i = 0; i<=index; i++) {
            result.add(myBlock[i]);
        }
        return result;
    }

    public boolean isContains(String s, String term1, String term2) {
        s = s.replaceAll("[`~!@#$^&*()=|{}:;,\\\\[\\\\].<>/?！￥…（）—_【】‘；：”“。，、？]", " ");
        String[] terms = s.split(" +");
        return (isContain(terms, term1) && isContain(terms, term2));
    }

    public boolean isContain(String[] terms, String term) {
        for (String s : terms) {
            if (s.equals(term))
                return true;
        }
        return false;
    }

    public static void main(String[] args) {
        String record1 = "Merged processes: a new condensed representation of Petri net behaviour. Victor_Khomenko,Alex_Kondratyev,Maciej_Koutny,Walter_Vogler， Acta Inf. 2006 43 307-330";
        String record2 = "Clustering Genes Using Heterogeneous Data Sources. Erliang_Zeng,Chengyong_Yang,Tao_Li,Giri_Narasimhan IJKDB 2010 1 12-28";
        String record3 = "Amjad Mehmood, Muhammad Muneer Umar, Houbing Song: ICMDS: Secure inter-cluster multiple-key distribution scheme for wireless sensor networks. Ad Hoc Networks 55: 97-106 (2017)";
        String record4 = "Jochen Neusser, Veronika Schleper: Numerical schemes for the coupling of compressible and incompressible fluids in several space dimensions. Applied Mathematics and Computation 304: 65-82 (2017)";
        Block block = new Block();
        List<MyBlock> blockRes = block.doBlock(record4);
        Iterator iterator = blockRes.iterator();
        while (iterator.hasNext()) {
            Object myBlock = iterator.next();
            System.out.println(myBlock.toString());
        }
//        record = record.replaceAll("[\\pP]", " ");
//        String[] terms = record.split(" +");
//        System.out.println(Arrays.toString(terms));

    }
}
