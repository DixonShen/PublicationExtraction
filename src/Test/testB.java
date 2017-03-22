package Test;

/**
 * Created by dixonshen on 2017/3/22.
 */
public class testB {

    public static void change(String s) {
        s = "zhangsan";
    }

    public static void main(String[] args) {
        String s = new String("lisi");
        System.out.println(s);
        change(s);
        System.out.println(s);
    }
}
