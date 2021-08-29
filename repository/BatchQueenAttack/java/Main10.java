package BatchQueenAttack.java;

import java.io.*;
import java.util.*;

public class Main10 {
    public static void main(String args[])throws IOException
    {
        Scanner in=new Scanner(System.in);
        int n=in.nextInt();
        int obs=in.nextInt();
        int x=in.nextInt();
        int y=in.nextInt();
        int obstacle[][]=new int[obs][2];
        int paths[]=new int[8];
        int distance[]=new int[8];
        for(int i=0;i<obs;i++)
        {
            obstacle[i][0]=in.nextInt();
            obstacle[i][1]=in.nextInt();
        }
        for(int i=0;i<obs;i++)
        {
            double m1,m2;
            m1=obstacle[i][1]-y;
            m2=obstacle[i][0]-x;
            double degrees=Math.toDegrees(Math.atan(m1/m2));
            //System.out.println("DEGREES:"+degrees);
            if(m1==0 && m2>0)
            {
                int d=obstacle[i][0]-x-1;
                if(paths[0]==0)
                    distance[0]=d;
                else
                if(d<distance[0])
                    distance[0]=d;
                paths[0]=1;
            }
            else
            if(m1==0 && m2<0)
            {
                int d=x-1-obstacle[i][0];
                if(paths[4]==0)
                    distance[4]=d;
                else
                if(d<distance[4])
                    distance[4]=d;
                paths[4]=1;
            }
            else
            if(m1>0 && m2==0)
            {
                int d=obstacle[i][1]-y-1;
                if(paths[2]==0)
                    distance[2]=d;
                else
                if(d<distance[2])
                    distance[2]=d;
                paths[2]=1;
            }
            else
            if(m1<0 && m2==0)
            {
                int d=y-1-obstacle[i][1];
                if(paths[6]==0)
                    distance[6]=d;
                else
                if(d<distance[6])
                    distance[6]=d;
                paths[6]=1;
            }
            else
            if(degrees%45==0)
            {
                if(m1>0 && m2 >0)
                {
                    int d=(int)((Math.sqrt(Math.pow((obstacle[i][0]-x),2)+Math.pow((obstacle[i][1]-y),2)))/(Math.pow(2,0.5)))-1;
                    if(paths[1]==0)
                        distance[1]=d;
                    else
                    if(d<distance[1])
                        distance[1]=d;
                    paths[1]=1;
                }
                if(m1<0 && m2 <0)
                {
                    //System.out.println("yeah!");
                    double dd=((Math.sqrt(Math.pow((obstacle[i][0]-x),2)+Math.pow((obstacle[i][1]-y),2)))/(Math.pow(2,0.5)))-1;
                    //System.out.println("D:"+dd);
                    int d=(int)Math.ceil(dd);
                    //System.out.println("D:"+d);
                    if(paths[5]==0)
                        distance[5]=d;
                    else
                    if(d<distance[5])
                        distance[5]=d;
                    paths[5]=1;
                }
                if(m1<0 && m2>0)
                {
                    //System.out.println("yeah!");
                    int d=(int)((Math.sqrt(Math.pow((obstacle[i][0]-x),2)+Math.pow((obstacle[i][1]-y),2)))/(Math.pow(2,0.5)))-1;
                    //System.out.println("D:"+d);
                    if(paths[7]==0)
                        distance[7]=d;
                    else
                    if(d<distance[7])
                        distance[7]=d;
                    paths[7]=1;
                }
                if(m1>0 && m2<0)
                {
                    int d=(int)((Math.sqrt(Math.pow((obstacle[i][0]-x),2)+Math.pow((obstacle[i][1]-y),2)))/(Math.pow(2,0.5)))-1;
                    if(paths[3]==0)
                        distance[3]=d;
                    else
                    if(d<distance[3])
                        distance[3]=d;
                    paths[3]=1;
                }
            }
        }
        int sum=0;
        /*for(int i=0;i<8;i++)
        System.out.print(paths[i]+"\t");
        System.out.println();
        for(int i=0;i<8;i++)
        System.out.print(distance[i]+"\t");*/
        for(int i=0;i<8;i++)
        {
            if(paths[i]==1)
                sum+=distance[i];
            else
            {
                if((i==0))
                    sum+=n-x;
                else
                if((i==4))
                    sum+=x-1;
                else
                if((i==2))
                    sum+=n-y;
                else
                if(i==6)
                    sum+=y-1;
                else
                if((i==1))
                {
                    sum+=n-Math.max(x,y);
                }
                else
                if((i==3))
                {
                    int a=x;int b=y;
                    a=y;
                    b=n-x+1;
                    sum+=n-Math.max(a,b);
                }
                else
                if((i==5))
                {
                    int a=x;int b=y;
                    a=n-x+1;
                    b=n-y+1;
                    sum+=n-Math.max(a,b);
                }
                else
                if(i==7)
                {
                    int a=x;int b=y;
                    a=n-y+1;
                    b=x;
                    sum+=n-Math.max(a,b);
                }
            }
        }
        System.out.println(sum);
    }
}