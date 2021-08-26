// Game Of Thrones Edition
package BatchWebOfLies.java;


import java.util.*;

public class Main3 {
        
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        int n = scn.nextInt();
        int m = scn.nextInt();

        int[] freq = new int[n];
        int count = n;
        for(int i=0; i<m; i++)
        {
            int u = scn.nextInt();
            int v = scn.nextInt();

            freq[Math.min(u,v)-1] +=1;
            if(freq[Math.min(u,v)-1] == 1) count-=1;
        }

        int queries = scn.nextInt();
        
        for(int i=0; i<queries; i++)
        {            
            int type = scn.nextInt();
            if(type == 1)
            {
                int u = scn.nextInt();
                int v = scn.nextInt();
                freq[Math.min(u, v)-1] += 1;
                if(freq[Math.min(u, v)-1] == 1) count-=1;
            }
            else if(type == 2)
            {
                int u = scn.nextInt();
                int v = scn.nextInt();
                freq[Math.min(u, v)-1] -= 1;
                if(freq[Math.min(u,v)-1] == 0) count+=1;
            }
            else if(type==3)
            {                                
                System.out.println(count);
            }
        }        
        scn.close();
    }
}