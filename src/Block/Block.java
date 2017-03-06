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
        this.mKB = new CreateKB().create();
    }

    /**
     *
     * @param record
     * @return
     */
    public List<MyBlock> doBlock(String record) {
        record = record.replaceAll(",", " ");
        System.out.println(record);
        System.out.println("start blocking step!");
        List<MyBlock> result = new ArrayList<>();
        String[] terms = record.split(" ");
        MyBlock[] myBlock = new MyBlock[terms.length];
        int index = 0;
        myBlock[0] = new MyBlock(terms[0]);
        for (int i=1; i<terms.length; i++) {
            isFound = false;
            OUT:
            for (List<String> valueSet : mKB.values()) {
                for (String s : valueSet.toArray(new String[0])) {
                    if (s.contains(terms[i-1]) && s.contains(terms[i])) {
                        System.out.println(terms[i-1] + ", " + terms[i] + "; " + s);
                        myBlock[index].addOneString(terms[i]);
                        isFound = true;
                        break OUT;
                    }
                }
            }
            if (isFound == false) {
                index++;
                myBlock[index] = new MyBlock(terms[i]);
            }
        }
        for (int i = 0; i<=index; i++) {
            result.add(myBlock[i]);
        }
        return result;
    }

    public static void main(String[] args) {
        String record = "Parallel Integer Sorting and Simulation Amongst CRCW Models. Sanjeev Saxena, Acta Inf., 1996, 607-619, 33.";
        Block block = new Block();
        List<MyBlock> blockRes = block.doBlock(record);
        Iterator iterator = blockRes.iterator();
        while (iterator.hasNext()) {
            Object myBlock = iterator.next();
            System.out.println(myBlock.toString());
        }

    }
}
