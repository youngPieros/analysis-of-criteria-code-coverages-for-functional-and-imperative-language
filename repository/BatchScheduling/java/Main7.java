import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Main7 {

    public static void main(String[] args) {

        int[] D,M,H;
        int current = 0,mintask,i;
        String[] input;
        Scanner s = new Scanner(System.in);
        int size = s.nextInt();s.nextLine();

        D = new int[size];
        M = new int[size];
        H = new int[size];

        for(i=0;i<size;i++)
        {
            //System.out.println(s.nextLine());
            input = s.nextLine().split(" ");
            D[i] = Integer.parseInt(input[0]);
            M[i] = Integer.parseInt(input[1]);
            H[i] = D[i] - M[i];
        }

        for(int N = 1;N<=size;N++)
        {
            schedule(Arrays.copyOf(D,N),Arrays.copyOf(M,N),Arrays.copyOf(H,N),N);


        }
    }

    static void schedule(int[] D, int[] M, int[] H, int N)
    {
        int mintask, i;
        while(!tasks_done(M,N))
        {
            //--- Find task with min H
            mintask = 0;

            for(i=1;i<N;i++)
            {
                if(H[mintask] > H[i] && M[i]>0)
                    mintask = i;
            }
            //--- Schedule it, decrementing its M, and decrementing all Ds
            //System.out.println("Scheduling task " + (mintask+1) + ", effort left = " + (M[mintask]-1) + ", minutes left = " + (D[mintask]-1));
            M[mintask]--;
            if(M[mintask] == 0)
                D[mintask]--;
            for(i=0;i<N;i++)
            {
                if(M[i] > 0)
                    D[i]--;
                H[i] = D[i] - M[i];
            }
        }

        //--- Print abs of min of D
        mintask = 0;
        //System.out.println("D -> " + D[0] + " ");
        for(i=1;i<N;i++)
        {
            //System.out.print(D[i] + " ");
            if(D[mintask] > D[i])
                mintask = i;
        }
        System.out.println(-1*(D[mintask]));
    }

    static boolean tasks_done(int[] M, int N)
    {

        for(int i=0;i<N;i++)
        {
            if(M[i] > 0)
            {
                //System.out.println("Task " + (i+1) + " not done : " + M[i]);
                return false;
            }

        }
        return true;
    }
}