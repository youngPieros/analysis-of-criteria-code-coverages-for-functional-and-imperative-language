/*
Please write complete compilable code.
Your class should be named Solution
Read input from standard input (STDIN) and print output to standard output(STDOUT).
For more details, please check http://www.interviewstreet.com/recruit/challenges/faq/view#stdio
*/
package BatchScheduling.java;

import java.util.*;

public class Main3 {

    public static ArrayList<Integer> total = new ArrayList<Integer>();
    public static ArrayList<Integer> delay = new ArrayList<Integer>();
    public static ArrayList<Integer> data = new ArrayList<Integer>();
    public static int maxSize=100010;
    public static int dataSize=317;
    public static int prevAns=0;

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        StringBuffer answer = new StringBuffer();
        for(int i=0;i<dataSize;i++){
            total.add(0);	delay.add(-1000000000);
        }
        for(int i=0;i<maxSize;i++) data.add(0);
        int totalData = sc.nextInt();
        for(int i=0;i<totalData;i++){
            int temp = findAns(sc.nextInt(),sc.nextInt());
            if(temp>prevAns) prevAns = temp;
            answer.append(Integer.toString(prevAns)+"\n");
        }
        System.out.println(answer.toString());
    }

    private static int findAns(int deadline, int time){
        int batch = deadline/dataSize;
        int maxDelay = -1000000000;
        int size = dataSize*(batch+1);
        if(size>maxSize) size = maxSize;
        for(int i=deadline;i<size;i++){
            data.set(i,data.get(i)+time);
            int tempdelay = maxDelay;
            if(batch==0) tempdelay = data.get(i) - i;
            else tempdelay = data.get(i) +total.get(batch-1) - i;
            if(tempdelay>maxDelay) maxDelay = tempdelay;
        }
        if(maxDelay>delay.get(batch)) delay.set(batch,maxDelay);
        for(int i=batch;i<dataSize;i++){
            total.set(i,total.get(i)+time);
            if(i>batch) delay.set(i,delay.get(i)+time);
            if(maxDelay<delay.get(i)) maxDelay = delay.get(i);
        }
        if(maxDelay<0) return 0;
        return maxDelay;
    }
}