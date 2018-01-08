package Reinforcement;

import Block.MyBlock;
import KnowledgeBase.CreateKB;

import java.rmi.MarshalledObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dixonshen on 2017/3/27.
 */
public class PSModel {

    // 统计信息
    public Map<String, Map<String, Integer>> t = new HashMap<>();
    public Map<String, Map<Integer, Integer>> p = new HashMap<>();

    // 概率矩阵
    public static Map<String, Map<String, Double>> T = new HashMap<>();
    public static Map<String, Map<Integer, Double>> P = new HashMap<>();

    public static String[] labelSet = {"begin", "title", "author", "journal",
                                        "year", "pages", "volume", "end"};

    private int labelCount = 8;
    private int longestLength;

    /**
     * 插入开始结束block，(isDiscard为true时)丢弃未匹配的block
     * @param records
     * @return
     */
    public List<List<MyBlock>> PrepareBlocks(List<List<MyBlock>> records,  boolean isDiscard) {
        List<List<MyBlock>> res = new ArrayList<>();
        int size = 0;
        for (List<MyBlock> record: records) {
            List<MyBlock> newRecord = new ArrayList<>();
            MyBlock beginBlock = new MyBlock(null);
            MyBlock endBlock = new MyBlock(null);
            beginBlock.setLabel("begin");
            endBlock.setLabel("end");
            newRecord.add(beginBlock);
            for (MyBlock block : record) {
                // 丢弃未匹配的block，即label为None
                if (isDiscard) {
                    if(!block.getLabel().equals("None"))
                        newRecord.add(block);
                } else {
                    newRecord.add(block);
                }
            }
            newRecord.add(endBlock);
            res.add(newRecord);
            size = newRecord.size();
            if (size > longestLength)
                longestLength = size;
        }
        System.out.println(longestLength);
        return res;
    }

    /**
     * 初始化模型（参数归为0）
     */
    public void InitModel() {
        for (String label1 : labelSet) {
            Map<String, Integer> map1 = new HashMap<>();
            Map<Integer, Integer> map2 = new HashMap<>();
            for (String label2: labelSet) {
                map1.put(label2, 0);
            }
            for (int i=0; i<longestLength; i++) {
                map2.put(i, 0);
            }
            t.put(label1, map1);
            p.put(label1, map2);
        }
    }

    /**
     * 使用matching的结果（去除未匹配）构建模型
     * @param records
     */
    public void CreateModel(List<List<MyBlock>> records) {
        for (List<MyBlock> record : records) {
            for (int i=1; i<record.size(); i++) {
                MyBlock curBlock = record.get(i);
                MyBlock preBlock = record.get(i-1);

                Map<String, Integer> temp1 = t.get(preBlock.getLabel());
                int count1 = temp1.get(curBlock.getLabel());
                count1++;
                temp1.put(curBlock.getLabel(), count1);
                if (curBlock.getLabel().equals("end"))
                    continue;
                else {
                    Map<Integer, Integer> temp2 = p.get(curBlock.getLabel());
                    int count2 = temp2.get(i);
                    count2++;
                    temp2.put(i, count2);
                }
            }
        }

        Map<String, Integer> tCount = new HashMap<>();
        Map<Integer, Integer> pCount = new HashMap<>();

        for (String label1 : labelSet) {
            Map<String, Integer> temp1 = t.get(label1);
            int count = 0;
            for (String label2: labelSet) {
                count += temp1.get(label2);
            }
            tCount.put(label1, count);
        }
        for (int i=1; i<longestLength; i++) {
            int count = 0;
            for (String label : labelSet) {
                count += p.get(label).get(i);
            }
            pCount.put(i, count);
        }

        for (String label : labelSet) {
            if (label.equals("end")) continue;
            Map<String, Double> temp1 = new HashMap<>();
            for (String label2 : labelSet) {
                double fraction = tCount.get(label);
                if (fraction == 0.0)
                    temp1.put(label2, 0.0);
                else {
                    double prob = (double)t.get(label).get(label2) / (double)tCount.get(label);
                    temp1.put(label2, prob);
                }
            }
            T.put(label, temp1);
            Map<Integer, Double> temp2 = new HashMap<>();
            for (int i=1; i<longestLength; i++) {
                double fraction = pCount.get(i);
                if (fraction == 0.0)
                    temp2.put(i, 0.0);
                else {
                    double prob = (double)p.get(label).get(i) / (double)pCount.get(i);
                    temp2.put(i, prob);
                }
            }
            P.put(label, temp2);
        }
    }

    public static void main(String[] args) {
//        InitModel();

    }
}
