package BatchWebOfLies.java;

import java.io.*;
import java.util.*;
public class Main5 {
	public static void main (String[] args) throws Exception {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] str = br.readLine().split(" ");
		int n = Integer.parseInt(str[0]);
		int m = Integer.parseInt(str[1]);
		int[] count = new int[n+1];
		int nonZero = 0;
	    for(int i=0;i<m;i++) {
	        str = br.readLine().split(" ");
	        int u = Integer.parseInt(str[0]);
	        int v = Integer.parseInt(str[1]);
	        int min = Math.min(u,v);
	        if(count[min]==0)
	        nonZero++;
	        count[min]++;
	    }
	    int q = Integer.parseInt(br.readLine());
	    for(int i=0;i<q;i++) {
	       str = br.readLine().split(" ");
	       if(Integer.parseInt(str[0])==1) {
	           int u = Integer.parseInt(str[1]);
	           int v = Integer.parseInt(str[2]);
	           int min = Math.min(u,v);
    	        if(count[min]==0)
    	        nonZero++;
    	        count[min]++;
	       } else if(Integer.parseInt(str[0])==2) {
	           int u = Integer.parseInt(str[1]);
	           int v = Integer.parseInt(str[2]);
	           int min = Math.min(u,v);
    	        if(count[min]==1)
    	        nonZero--;
	            count[min]--;
	       } else if(Integer.parseInt(str[0])==3) {
	           sb.append((n-nonZero)+"\n");
	       }
	    }
	    System.out.print(sb.toString());
	}
}