package BatchQueenAttack.java;

import java.io.*;
import java.util.*;

public class Main9 {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int k = scanner.nextInt();
        int rQueen = scanner.nextInt();
        int cQueen = scanner.nextInt();
        int count = 0;
        int down =-1;
        int up = -1;
        int left =-1;
        int right =-1;
        int ur =-1;
        int ul =-1;
        int ll =-1;
        int lr =-1;
        for(int i = 0; i<k;i++){
            int rObs = scanner.nextInt();
            int cObs = scanner.nextInt();

            //up
            if(rQueen<rObs&&cQueen==cObs){
                up = rObs-rQueen-1;
                //       System.out.println(up + "up");
            }
            //down
            if(rQueen>rObs&&cQueen==cObs){
                down = rQueen-rObs-1;
                //      System.out.println(down+ "down");
            }
            //left
            if(rQueen==rObs&&cQueen>cObs){
                left = cQueen-cObs-1;
                //     System.out.println(left+ "left");
            }
            //right
            if(rQueen==rObs&&cQueen<cObs){
                right = cObs-cQueen-1;
                //       System.out.println(right+ "right");
            }
            //ur
            if(((rObs-rQueen)==(cObs-cQueen))&&(rObs-rQueen)>0){
                if(ur==-1||ur > Math.min(rObs-rQueen-1,cObs-cQueen-1))
                    ur =Math.min(rObs-rQueen-1,cObs-cQueen-1);
                //         System.out.println(ur+ "ur"+ " robs " + rObs + " cobs "+cObs);

            }
            //ul
            if((rObs-rQueen)==(cQueen-cObs)&&(rObs-rQueen)>0){
                if(ul==-1||ul > Math.min(rObs-rQueen-1,cQueen-cObs-1))
                    ul =  Math.min(rObs-rQueen-1,cQueen-cObs-1);
                //      System.out.println(ul+ "ul");
            }
            //ll
            if((rQueen-rObs)==(cQueen-cObs)&&(rQueen-rObs)>0){
                if(ll==-1||ll > Math.min(rQueen-rObs-1,cQueen-cObs-1))
                    ll = Math.min(rQueen-rObs-1,cQueen-cObs-1);
                //        System.out.println(ll+ "ll");
            }
            //lr
            if((rQueen-rObs)==(cObs-cQueen)&&(rQueen-rObs)>0){
                if(lr==-1||lr > Math.min(rQueen-rObs-1,cObs-cQueen-1))
                    lr = Math.min(rQueen-rObs-1,cObs-cQueen-1);
                //         System.out.println(lr+ "lr"+ " robs " + rObs + " cobs "+cObs);
            }


        }

        count+=(down!=-1)?down:rQueen-1;
        count+=(up!=-1)?up:n-rQueen;
        count+=(left!=-1)?left:cQueen-1;
        count+=(right!=-1)?right:n-cQueen;
        count+=(ur!=-1)?ur:Math.min(n-rQueen,n-cQueen);
        count+=(ul!=-1)?ul:Math.min(n-rQueen,cQueen-1);
        count+=(ll!=-1)?ll:Math.min(rQueen-1,cQueen-1);
        count+=(lr!=-1)?ll: Math.min(rQueen-1,n-cQueen);
        System.out.println(count);
    }
}