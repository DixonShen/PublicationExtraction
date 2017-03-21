package Matching;

import Block.MyBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by leeon on 2017/3/17.
 */
public class Matching {

    public static List<Vocabulary> vocabularies = new ArrayList<>();

    public Matching(Map<String, List<String>> mKB) {
        for (String label : mKB.keySet()) {
            vocabularies.add(new Vocabulary(mKB.get(label), label));
        }
        System.out.println("Initializing Vocabulary list completely!");
    }

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
            if (ch < '0' || ch > '9')
                return false;
        }
        return true;
    }

    /**
     * matching text values
     * @param myBlock
     * @param wordsSet
     * @return
     */
    public static double CalculateAF(MyBlock myBlock, Vocabulary wordsSet) {
        return 0;
    }

    /**
     * matching numeric values
     * @param myBlock
     * @param wordsSet
     * @return
     */
    public static double NumericMatching(MyBlock myBlock, Vocabulary wordsSet) {
        return 0;
    }

    /**
     * calculate the fitness between tern t and attr a
     * @param term
     * @param wordsSet
     * @return
     */
    public static double CalculateFitness(String term, Vocabulary wordsSet) {
        return (Frequency(term, wordsSet) / TotalFrequency(term, vocabularies) * Frequency(term, wordsSet) / MaxFrequency(wordsSet));
    }

    // the number of occurrences of one attr in KB which contains term t
    public static int Frequency(String term, Vocabulary wordsSet) {
        return 0;
    }

    // highest frequency of any term among the occurrences of one attr
    public static int MaxFrequency(Vocabulary wordsSet) {
        return 0;
    }

    // total number of occurrences of term t in all attrs
    public static int TotalFrequency(String term, List<Vocabulary> vocabularyList) {
        return 0;
    }

    public static void doMatching(List<MyBlock> blocks, List<Vocabulary> vocabularyList) {
        for (MyBlock mb : blocks.toArray(new MyBlock[0])) {
            if (getType(mb)) {
                double nmSimilarity = 0;
                for (Vocabulary vol : vocabularyList) {
                    double temp = NumericMatching(mb, vol);
                    if (temp > nmSimilarity) {
                        nmSimilarity = temp;
                        mb.setLabel(vol.label);
                    }
                }
            } else {
                double af = 0;
                for (Vocabulary vol : vocabularyList) {
                    double temp = CalculateAF(mb, vol);
                    if (temp > af) {
                        af = temp;
                        mb.setLabel(vol.label);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {

    }
}
