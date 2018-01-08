package Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dixonshen on 2017/3/22.
 */
public class testB {

    public double setAverage(int[] s) {
        double sum = 0;
        for (double a : s) {
            sum += a;
        }
        return (double)(sum / s.length);
    }

    public double setStandardDeviation(int[] s, double average){
        double sum = 0;
        for (int a : s) {
            sum += ((double)a - average) * ((double)a - average);
        }
        return Math.sqrt(sum / s.length);
    }

    public static void main(String[] args) {
        double[] s = {1, 2};
        List<String> aList;

    }
}
