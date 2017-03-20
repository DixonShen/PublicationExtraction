package Matching;

import Block.MyBlock;

import java.util.List;
import java.util.Map;

/**
 * Created by leeon on 2017/3/17.
 */
public class Matching {

    /**
     * judge if the string contains numbers only
     * @param myBlock
     * @return
     */
    public static boolean getType(MyBlock myBlock) {
        String s = myBlock.getContents();
        s = s.replaceAll(" ", "");
        char[] chs = s.replaceAll("[`~!@#$^&*()=|{}:;,\\\\[\\\\].<>/?！￥…（）—_【】‘；：”“。，、？]", "").toCharArray();
        for (char ch : chs) {
            if (ch < '1' || ch > '9')
                return false;
        }
        return true;
    }

    public static double CalculateAF(MyBlock myBlock, List<String> valueSet) {

    }

    public static double NumericMatching(MyBlock myBlock, List<String> valueSet) {
        return 0;
    }

    public static void

    public static void doMatching(List<MyBlock> blocks, Map<String, List<String>> mKB) {
        for (MyBlock mb : blocks.toArray(new MyBlock[0])) {
            if (getType(mb)) {
                double nmSimilarity = 0;
                for (String s : mKB.keySet()) {
                    if (NumericMatching(mb, mKB.get(s)) > nmSimilarity)
                        mb.setLabel(s);
                }
            } else {
                double af = 0;
                for (String s : mKB.keySet()) {
                    if (CalculateAF(mb, mKB.get(s)) > af)
                        mb.setLabel(s);
                }
            }
        }
    }

    public static void main(String[] args) {

    }
}
