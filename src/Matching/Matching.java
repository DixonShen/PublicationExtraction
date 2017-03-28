package Matching;

import Block.Block;
import Block.MyBlock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
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
        s = s.replaceAll(" +", "");
        char[] chs = s.replaceAll("[`~!@#$^&*()=|{}:;,\\\\[\\\\].<>/?！￥…（）—_【】‘；：”“。，、？]", "").replaceAll("-", "").toCharArray();
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
        String[] terms = myBlock.getContents().split(" +");
        double af = 0;
        for (String term : terms) {
            af += CalculateFitness(term, wordsSet);
        }
        return af / terms.length;
    }

    /**
     * matching numeric values
     * @param myBlock
     * @param wordsSet
     * @return
     */
    public static BigDecimal NumericMatching(MyBlock myBlock, Vocabulary wordsSet) {
        if (wordsSet.average.doubleValue() == 0.0 || wordsSet.standardDeviation.doubleValue() == 0.0) return new BigDecimal("0.0");
        String terms = myBlock.getContents();
        terms = terms.replaceAll("[`~!@#$^&*()=|{}:;,\\\\[\\\\].<>/?！￥…（）—_【】‘；：”“。，、？]", " ");
        terms = terms.replaceAll(" +", "");
        terms = terms.replaceAll("-", "");
        if (terms.isEmpty()) return new BigDecimal("0.0");
//        if (terms == "") return new BigDecimal("0.0");
//        double number = Double.parseDouble(terms);
        BigDecimal number = new BigDecimal(Double.toString(Double.parseDouble(terms)));
//        double exp = (number - wordsSet.average) / (2 * wordsSet.standardDeviation * wordsSet.standardDeviation);
//        double maxProbDensity = Math.sqrt(2 * Math.PI * wordsSet.standardDeviation * wordsSet.standardDeviation);
        BigDecimal exp;
        BigDecimal diff = number.subtract(wordsSet.average).abs();
        BigDecimal h = wordsSet.standardDeviation.multiply(wordsSet.standardDeviation);
        h = h.multiply(new BigDecimal("2.0"));
        exp = diff.divide(h, 10, BigDecimal.ROUND_HALF_UP);
        System.out.println(myBlock.getContents() + ", " + wordsSet.label + ", " + "exp: " + exp.doubleValue());
        BigDecimal res = new BigDecimal(Double.toString(Math.pow(Math.E, -exp.doubleValue())));
        System.out.println("e^exp: " + res);
        return res;
    }

    /**
     * calculate the fitness between tern t and attr a
     * @param term
     * @param wordsSet
     * @return
     */
    public static double CalculateFitness(String term, Vocabulary wordsSet) {
        double freq = Frequency(term, wordsSet);
        double maxFreq = wordsSet.maxFrequency;
        double totalFreq = TotalFrequency(term, vocabularies);
//        if (wordsSet.label.equals("title")) {
//            System.out.println(freq);
//            System.out.println(maxFreq);
//            System.out.println(totalFreq);
//        }
        if (freq == 0 || maxFreq == 0 || totalFreq == 0)
            return 0;

        double res = freq / totalFreq * freq / maxFreq;

        return res;
    }

    // the number of occurrences of one attr in KB which contains term t
    public static int Frequency(String term, Vocabulary wordsSet) {
        if (wordsSet.words.contains(term)) {
            return wordsSet.frequency.get(term);
        }
        return 0;
    }

    // total number of occurrences of term t in all attrs
    public static int TotalFrequency(String term, List<Vocabulary> vocabularyList) {
        int total = 0;
        for (Vocabulary vocabulary : vocabularyList) {
            total += Frequency(term, vocabulary);
        }
        return total;
    }

    public static void doMatching(List<MyBlock> blocks, List<Vocabulary> vocabularyList) {
        for (MyBlock mb : blocks.toArray(new MyBlock[0])) {
            if (getType(mb)) {
                BigDecimal nmSimilarity = new BigDecimal("0.0");
                for (Vocabulary vol : vocabularyList) {
//                    if (vol.label.equals("title") ||
//                            vol.label.equals("journal") ||
//                            vol.label.equals("author"))
//                        continue;
                    BigDecimal temp = NumericMatching(mb, vol);
                    System.out.println(mb.getContents() + ", " + vol.label + ": " + temp);
                    if (temp.compareTo(nmSimilarity) == 1) {
                        nmSimilarity = temp;
                        mb.setLabel(vol.label);
                        mb.setSimFunc("Numeric");
                    }
                }
            } else {
                double af = 0;
                for (Vocabulary vol : vocabularyList) {
                    double temp = CalculateAF(mb, vol);
                    System.out.println(mb.getContents() + ", " + vol.label + ": " + temp);
                    if (temp > af) {
                        af = temp;
                        mb.setLabel(vol.label);
                        mb.setSimFunc("AF");
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Block block = new Block();
        Matching match = new Matching(block.getmKB());
        System.out.println("\n");
        for (Vocabulary vocabulary : match.vocabularies) {
            System.out.println(vocabulary.label.toUpperCase() + ": " + "size of words " + vocabulary.words.size());
            System.out.println(vocabulary.label.toUpperCase() + ": " + "size of numbers " + vocabulary.numbers.size());
            System.out.println(vocabulary.label.toUpperCase() + ": " + "average value " + vocabulary.average);
            System.out.println(vocabulary.label.toUpperCase() + ": " + "standard deviation value " + vocabulary.standardDeviation);
            System.out.println("\n");
//            if (vocabulary.label.equals("volume")) {
//                for (int s : vocabulary.numbers)
//                    System.out.println(s);
//                System.out.println("=============");
//            }
        }

        String record1 = "Merged processes: a new condensed representation of Petri net behaviour. Victor_Khomenko,Alex_Kondratyev,Maciej_Koutny,Walter_Vogler， Acta Inf. 2006 43 307-330";
        String record2 = "Clustering Genes Using Heterogeneous Data Sources. Erliang_Zeng,Chengyong_Yang,Tao_Li,Giri_Narasimhan IJKDB 2010 1 12-28";
        String record3 = "Amjad Mehmood, Muhammad Muneer Umar, Houbing Song: ICMDS: Secure inter-cluster multiple-key distribution scheme for wireless sensor networks. Ad Hoc Networks 55: 97-106 (2017)";
        String record4 = "Jochen Neusser, Veronika Schleper: Numerical schemes for the coupling of compressible and incompressible fluids in several space dimensions. Applied Mathematics and Computation 304: 65-82 (2017)";

        List<String> records = new ArrayList<>();
        records.add(record1);
        records.add(record2);
        records.add(record3);
        records.add(record4);
        for (String record : records) {
            List<MyBlock> blocks = block.doBlock(record);
            doMatching(blocks, vocabularies);

            Iterator iterator = blocks.iterator();
            while (iterator.hasNext()) {
                Object myBlock = iterator.next();
                System.out.println(myBlock.toString());
            }
        }

    }
}
