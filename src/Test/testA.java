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
        for (int i=0; i<=5; i++) {
            OUT:
            for (int j=0; j<5; j++) {
                for (int k=0; k<5; k++) {
                    if (k == 3) {
                        break OUT;
                    }
                    System.out.println("world");
                }
            }
            System.out.println("hello");
        }
    }
}
