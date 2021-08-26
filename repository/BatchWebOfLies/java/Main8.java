package BatchWebOfLies.java;

import java.io.*;
import java.util.*;

public class Main8 {
    public static void main(String[] args) throws Exception {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer member_map = new StringTokenizer(input.readLine());
        int N = Integer.parseInt(member_map.nextToken());
        int M = Integer.parseInt(member_map.nextToken());
        int[] fight = new int[N];
        for(int i =0;i<M;i++){
            member_map = new StringTokenizer(input.readLine());
            int a = Integer.parseInt(member_map.nextToken())-1;
            int b = Integer.parseInt(member_map.nextToken())-1;
            fight[Math.min(a,b)]++;
        }
        int alive = 0;
        for(int i =0;i<N;i++){
            if(fight[i]==0) alive++;
        }
        StringBuilder  result = new StringBuilder();
        int query = Integer.parseInt(input.readLine());
        while(query-->0){ //add
            member_map = new StringTokenizer(input.readLine());
            int type = Integer.parseInt(member_map.nextToken());
            if(type==1){
                int a = Integer.parseInt(member_map.nextToken())-1;
                int b = Integer.parseInt(member_map.nextToken())-1;
                fight[Math.min(a,b)]++;
                if(fight[Math.min(a,b)]==1) alive--;
                
            }
            else if(type==2){ //remove
                int a = Integer.parseInt(member_map.nextToken())-1;
                int b = Integer.parseInt(member_map.nextToken())-1;
                fight[Math.min(a,b)]--;
                if(fight[Math.min(a,b)]==0) alive--;
            }
            else{
                result.append(alive+"\n");
            }
        }
        System.out.println(result);

    }
}
