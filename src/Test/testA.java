package Test;

import com.sun.deploy.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Z003R98D on 2/24/2017.
 */
public class testA {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("hello");
        list.add("world!");
        list.add("DixonShen");

        String res = "";

        for (Object s : list.toArray()){
            res += (String)s;
        }

        System.out.println(res);
        System.out.print(StringUtils.join(list, " "));
    }
}
