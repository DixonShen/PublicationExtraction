package Reinforcement;

import Block.MyBlock;
import KnowledgeBase.CreateKB;
import Matching.Matching;
import Matching.Vocabulary;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dixonshen on 2017/3/28.
 */
public class LabelReinforcement {

    public static void Reinforcement(List<List<MyBlock>> records, List<Vocabulary> vocabularyList) {
        for (List<MyBlock> record : records) {
            for (int i=1; i<record.size()-1; i++) {
                MyBlock curBlock = record.get(i);
                MyBlock preBlock = record.get(i-1);
                Map<String, BigDecimal> sims = simRes(curBlock, vocabularyList);
                double FS = 0.0;
                for (String label : CreateKB.AttributeSet) {
                    double temp = 1 - ((1 - sims.get(label).doubleValue()) * (1 - PSModel.T.get(preBlock.getLabel()).get(label))
                            * (1 - PSModel.P.get(label).get(i)));
                    if (temp > FS){
                        FS = temp;
                        curBlock.setLabel(label);
                    }
                }
            }
        }
    }

    public static Map<String, BigDecimal> simRes(MyBlock myBlock, List<Vocabulary> vocabularyList) {
        Map<String, BigDecimal> res = new HashMap<>();
        if (myBlock.getSimFunc().equals("Numeric")) {
            for (int i=0; i < vocabularyList.size(); i++) {
                Vocabulary tempVoc = vocabularyList.get(i);
                BigDecimal temp = Matching.NumericMatching(myBlock, tempVoc);
                res.put(tempVoc.label, temp);
            }
        }
        else {
            for (int i=0; i< vocabularyList.size(); i++) {
                Vocabulary tempVoc = vocabularyList.get(i);
                BigDecimal temp = new BigDecimal(Double.toString(Matching.CalculateAF(myBlock, tempVoc)));
                res.put(tempVoc.label, temp);
            }
        }
        return res;
    }
}
