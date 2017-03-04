package Block;

import com.sun.deploy.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Z003R98D on 2/24/2017.
 */
public class MyBlock {

    private List<String> contents = new ArrayList<>();   // store the contents of the block
    private String label = "";   // note the result of matching step

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

}
