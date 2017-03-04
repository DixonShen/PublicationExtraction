package Block;

import KnowledgeBase.CreateKB;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dixonshen on 2017/2/28.
 */
public class Block {

    private Map<String, List<String>> mKB = new HashMap<>();

    public void doBlock(MyBlock myBlock) {

    }

    public static void main(String[] args) {
        Block block = new Block();
        block.mKB = new CreateKB().create();
    }
}
