package Test;

/**
 * Created by dixonshen on 2017/3/20.
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args)  {
//        Scanner in = new Scanner(System.in);
//        int maxQps= Integer.valueOf(in.nextLine());
//        final String[] rtList = in.nextLine().split(",");
//        final int requestNum = Integer.valueOf(in.nextLine());
//        final int threadNum = Integer.valueOf(in.nextLine());
//        System.out.println(doneTime(maxQps, rtList, requestNum, threadNum));
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("temp_combined_data.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                line = line.split("\t")[1];
                System.out.println(line);
//                records.add(line);
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 如果使用最优的最大吞吐量负载均衡算法，按照最优模型多久能够处理完所有请求，单位毫秒。
     * @return
     */
    static long doneTime(int maxQps,String[] rtList,int requestNum,int threadNum) {
        //TODO
        long totalTime = 0;
        for (int i = 0; i < requestNum; i++) {
//            int index = (int)Math.random()
        }
        return totalTime;
    }
}