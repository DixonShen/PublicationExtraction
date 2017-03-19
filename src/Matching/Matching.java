package Matching;

import Block.MyBlock;

import java.util.List;
import java.util.Map;

/**
 * Created by leeon on 2017/3/17.
 */
public class Matching {

    /**
     * tell if the string contains numbers only
     * @param s
     * @return
     */
    public static boolean getType(String s) {
        char[] chs = s.replaceAll("[`~!@#$^&*()=|{}:;,\\\\[\\\\].<>/?！￥…（）—_【】‘；：”“。，、？]", "").toCharArray();
        for (char ch : chs) {
            if (ch < '1' || ch > '9')
                return false;
        }
        return true;
    }

    public static double CalculateAF(String s, List<String> valueSet) {
        return 0;
    }

    public static void doMatching(List<MyBlock> blocks, Map<String, List<String>> mKB) {
        for (MyBlock mb : blocks.toArray(new MyBlock[0])) {

        }
    }
}
