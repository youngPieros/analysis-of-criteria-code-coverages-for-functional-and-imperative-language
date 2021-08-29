package BatchQueenAttack.java;

import java.util.*;

public class Main4 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();
        int yQueen = in.nextInt()-1;
        int xQueen = in.nextInt()-1;

        //coordinates of farthest accessible squares along each line of attack
        int aleft = 0; int aright = n-1; int atop = n-1; int abottom = 0;
        int atopright = yQueen+(n-1-xQueen) > n-1 ? xQueen+(n-1-yQueen) : n-1;
        //if (yQueen+(n-1-xQueen) > n-1) { atopright = xQueen+(n-1-yQueen); } //n-1;
        //else atopright = n-1;
        int atopleft = yQueen+xQueen > n-1 ? xQueen-(n-1-yQueen) : 0;
        int abottomright = yQueen-(n-1-xQueen) < 0 ? xQueen+yQueen : n-1;
        int abottomleft = yQueen-xQueen < 0 ? xQueen-yQueen : 0;
        for(int a0 = 0; a0 < k; a0++){
            int yObstacle = in.nextInt()-1;
            int xObstacle = in.nextInt()-1;
            // your code goes here
            if (xObstacle > xQueen) {//right (diag)
                if (yObstacle == yQueen) {//right
                    if (xObstacle <= aright) aright = xObstacle-1;
                }
                else if (xObstacle-xQueen == yObstacle-yQueen) {//right-top
                    if (xObstacle <= atopright) atopright = xObstacle-1;
                }
                else if (xObstacle-xQueen == -(yObstacle-yQueen)) {//right-down
                    if (xObstacle <= abottomright) abottomright = xObstacle-1;
                }
            }
            else if (xObstacle < xQueen) {//left (diag)
                if (yObstacle == yQueen) {//right
                    if (xObstacle >= aleft) aleft = xObstacle+1;
                }
                else if (xObstacle-xQueen == yObstacle-yQueen) {//left-bottom
                    if (xObstacle >= abottomleft) abottomleft = xObstacle+1;
                }
                else if (xObstacle-xQueen == -(yObstacle-yQueen)) {//left-top
                    if (xObstacle >= atopleft) atopleft = xObstacle+1;
                }
            }
            else {//up or down
                if (yObstacle > yQueen) {//up
                    if (yObstacle <= atop) atop = yObstacle-1;
                }
                else {//down
                    if (yObstacle >= abottom) abottom = yObstacle+1;
                }
            }
        }

        //System.out.printf("aright:%d\n",aright);
        //System.out.printf("aleft:%d\n",aleft);
        //System.out.printf("atop:%d\n",atop);
        //System.out.printf("abottom:%d\n",abottom);
        int answer = (aright-aleft)+(atop-abottom)+(atopright-atopleft)+(abottomright-abottomleft);
        System.out.println(answer);
    }
}