package Matching;

import Block.Block;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dixonshen on 2017/3/19.
 */
public class Vocabulary {

    public String label = "";
    public List<String> words = new ArrayList<>();
    public List<Integer> numbers = new ArrayList<>();
    public Map<String, Integer> frequency = new HashMap<>();

    public int maxFrequency = 0;

    public static final int DEF_DIV_SCALE = 10;

    // pages/year/volume等包含数字的属性集合的值的平均值
//    public double average = 0;
    public BigDecimal average = new BigDecimal("0.0");
    // pages/year/volume等包含数字的属性集合的值的标准差
//    public double standardDeviation = 0;
    public BigDecimal standardDeviation = new BigDecimal("0.0");

    public Vocabulary(List<String> list, String label) {
        this.label = label;
        System.out.println("Generating Vocabulary of " + label + "...");
        for (String s : list.toArray(new String[0])) {
            if (s == " ") continue;
            String mem = s;
            s = s.replaceAll("[`~!@#$^&*()=|{}:;,\\\\[\\\\].<>/?！￥…（）—_【】‘；：”“。，、？]", "");
            String[] terms = s.split(" +");
            for (String term : terms) {
                term = term.trim();
                if (term.equals("")) continue;
                if (isNumber(term)) {
//                    System.out.println(label + ": " +term);
                    if (label.equals("volume")) {
//                        System.out.println(term);
//                        System.out.println(mem);
                        String[] nums = term.split("-");
                        if (term.equals("")) continue;
                        for (String num : nums) {
                            numbers.add(Integer.parseInt(num));
                        }
                    } else {
                        term = term.replaceAll("-", "");
                        if (term.equals("")) continue;
                        numbers.add(Integer.parseInt(term));
                    }
                }
                else {
                    if (words.contains(term)) {
                        frequency.put(term, frequency.get(term) + 1);
                    }
                    else {
                        words.add(term);
                        frequency.put(term, 1);
                    }
                }
            }
        }
        if (label.equals("pages") || label.equals("volume") || label.equals("year")) {
            System.out.println("Calculating Average of " + label);
            setAverage();
            System.out.println(this.average.abs());
            System.out.println("Calculating Standard Deviation of " + label);
            setStandardDeviation();
            System.out.println(this.standardDeviation.abs());
        }
        this.maxFrequency = MaxFrequency(this);
        System.out.println("Generating Completely!");
    }

    public void setAverage() {
        BigDecimal sum = new BigDecimal(Double.toString(0));
        for (int a : numbers) {
            BigDecimal aa = new BigDecimal(Integer.toString(a));
            sum = sum.add(aa);
        }
        BigDecimal length = new BigDecimal(Integer.toString(numbers.size()));
        this.average = sum.divide(length, DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
    }

    public void setStandardDeviation(){
        BigDecimal sum = new BigDecimal(Double.toString(0));
        for (int a : numbers) {
            BigDecimal aa = new BigDecimal(Integer.toString(a));
            sum = sum.add(aa.subtract(average).multiply(aa.subtract(average)));
        }
        BigDecimal length = new BigDecimal(Integer.toString(numbers.size()));
        this.standardDeviation = new BigDecimal(Double.toString(Math.sqrt(sum.divide(length, DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP).doubleValue())));
    }

    // 判断字符串是否为数字
    public boolean isNumber(String s) {
        s = s.replaceAll("-", "");
        char[] chs = s.toCharArray();
        for (char ch : chs) {
            if (ch < '0' || ch > '9')
                return false;
        }
        return true;
    }

    // highest frequency of any term among the occurrences of one attr
    public static int MaxFrequency(Vocabulary wordsSet) {
        int max = 0;
        Map<String, Integer> freq = wordsSet.frequency;
        for (String s : freq.keySet()) {
            if (freq.get(s) > max) {
                max = freq.get(s);
            }
        }
        return max;
    }

    public static void main(String[] args) {
        Block mBlock = new Block();
        Map<String, List<String>> kb = mBlock.getmKB();
        for (String key : kb.keySet()) {
            Vocabulary vocabulary = new Vocabulary(kb.get(key), key);

        }
    }
}
