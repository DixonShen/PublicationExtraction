package Matching;

import Block.Block;

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

    // pages/year/volume等包含数字的属性集合的值的平均值
    public double average = 0;
    // pages/year/volume等包含数字的属性集合的值的标准差
    public double standardDeviation = 0;

    public Vocabulary(List<String> list, String label) {
        this.label = label;
        System.out.println("Generating Vocabulary of " + label + "...");
        for (String s : list.toArray(new String[0])) {
            if (s == " ") continue;
            s = s.replaceAll("[`~!@#$^&*()=|{}:;,\\\\[\\\\].<>/?！￥…（）—_【】‘；：”“。，、？]", " ");
            String[] terms = s.split(" +");
            for (String term : terms) {
                term = term.trim();
                if (term.equals("")) continue;
                if (isNumber(term)) {
//                    System.out.println(label + ": " +term);
                    term = term.replaceAll("-", "");
                    if (term.equals("")) continue;
                    numbers.add(Integer.parseInt(term));
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
            System.out.println(this.average);
            System.out.println("Calculating Standard Deviation of " + label);
            setStandardDeviation();
            System.out.println(this.standardDeviation);
        }
    }

    public void setAverage() {
        double sum = 0;
        for (int a : numbers) {
            sum += a;
            if (this.label.equals("pages")) {
                System.out.println(a);
                System.out.println(sum);
            }
        }
        this.average = (double)(sum/numbers.size());
    }

    public void setStandardDeviation(){
        double sum = 0;
        for (int a : numbers) {
            sum += Math.sqrt(((double)a - average) * ((double)a - average));
        }
        this.standardDeviation = Math.sqrt(sum / numbers.size());
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

    public static void main(String[] args) {
        Block mBlock = new Block();
        Map<String, List<String>> kb = mBlock.getmKB();
        for (String key : kb.keySet()) {
            Vocabulary vocabulary = new Vocabulary(kb.get(key), key);

        }
    }
}
