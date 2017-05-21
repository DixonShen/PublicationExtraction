package Block;

import com.sun.deploy.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Z003R98D on 2/24/2017.
 */
public class MyBlock {

    private List<String> contents = new ArrayList<>();   // store the contents of the block
    private String label = "None";   // note the result of matching step
//    private String realLabel = "";
    private String simFunc;

//    Map<String, List<String>> map = new HashMap<>();
//    private boolean isNum = false;  // note whether the contents of the block is num

    public MyBlock(String string){
        contents.add(string);
    }

    public String getContents() {
        return StringUtils.join(contents, " ");
    }

    public void addOneString(String string){
        contents.add(string);
    }

    public String getLabel(){
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setSimFunc(String simFunc) {
        this.simFunc = simFunc;
    }

    public String getSimFunc() {
        return this.simFunc;
    }

    @Override
    public String toString() {
        StringBuilder val = new StringBuilder(this.getLabel());
        val.append(":");
        for (String content : contents.toArray(new String[0]))
            val.append(" " + content);
        return val.toString();
    }

}
