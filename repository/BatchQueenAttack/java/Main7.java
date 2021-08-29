package BatchQueenAttack.java;

import java.util.*;

public class Main7 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();
        int rq = in.nextInt();
        int cq = in.nextInt();

        int[] flankdist = new int[9]; //1 to 8
        int qdistl = rq - 1;
        int qdistr = n - rq;
        int qdistb = cq - 1;
        int qdistt = n - cq;

        //continue initial flank lengths.
        //ohhh... maybe what is safe is... Math.min(qdistl,qdistb). i think that is safe..
        flankdist[1] = qdistt;
        flankdist[2] = Math.min(qdistt, qdistr);
        flankdist[3] = qdistr;
        flankdist[4] = Math.min(qdistr, qdistb);
        flankdist[5] = qdistb;
        flankdist[6] = Math.min(qdistb, qdistl);
        flankdist[7] = qdistl;
        flankdist[8] = Math.min(qdistl, qdistt); //these accurately measure how long the flank is.

        for(int a0 = 0; a0 < k; a0++){
            int ro = in.nextInt();
            int co = in.nextInt();

            int flank = getFlank(rq, cq, ro, co);
            if(flank == -1) continue;
            flankdist[flank] = Math.min(flankdist[flank], Math.max(Math.abs(ro - rq), Math.abs(co - cq)) - 1); //on flank constraints...

            //SIZE(n), #Obstacle
            //QueenLoc
            //ObstacleLoc
            //hmmm... welll.
            //Ohhhh dude. the queen location doesn't change over time.
            //for each obstacle, just figure out which "flank" of the queen it is intruding on
            //^ then regress that "flank" length if it actually reduces  that queens attacking range along this flank.
            //each obstacle can only reduce ONE flank.
            //the beginning attacking range for all 9 flanks is determined by the board boundaries and the queen location. that is all (probably need to take a min of 2 things... when going along the diagonals. or just check essentially if the queen is more RIGHT than she is UP... or some metric like this).
            //you can probably determine which flank the it is via the ratio of the delta + whether or not the x value is greater or not (is obstacle to RIGHT of queen or to LEFT of queen?)
            //^ but there is also vertical deltas to consider... lol.
            //^ create a subroutine to determine which of the 9 flanks the obstacle is blocking (if any)
            //^ computing the distance between a queen and a obstacle that is actually blocking is trivial.
            //essentially O(n) time computation not bad.
            //create a subroutine to figure out the original queen flank distance? (without any obstacles added yet)
        }
        //sum up the attack range.
        int res = 0;
        for(int i=1 ;i<9; i++){
            res += flankdist[i];
        }
        System.out.println(res);
    }



    //   8 1 2
    //   7 q 3   //-1 for none.
    //   6 5 4
    //we assume that both are within bounds of the board.
    //that that obstacle isn't on same position as the queen.
    public static int getFlank(int rq, int cq, int ro, int co){
        int rdiff = ro - rq;
        int cdiff = co - cq;
        if(ro == rq){ //same row. horizontal
            return co > cq ? 3 : 7;
        } else if(co == cq){ //same column. vertical.
            return ro > rq ? 1 : 5;
        } else if(rdiff == cdiff){ //positive difference. slope up
            return co > cq ? 2 : 6;
        } else if(rdiff == -cdiff){ //negative difference. slope down
            return co > cq ? 4 : 8;
        } else { //not on a flank.
            return -1;
        }
    }
}