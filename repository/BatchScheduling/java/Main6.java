package BatchScheduling.java;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Main6 {

    static List<Integer> finalDurationList = new ArrayList<Integer>();
    static List<Integer> finalDeadlineList = new ArrayList<Integer>();

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner sn = new Scanner(System.in);
        int numTasks = sn.nextInt();

        for(int i=0; i<numTasks; i++) {
            int deadline = sn.nextInt();
            int duration = sn.nextInt();
            finalDurationList.add(duration);
            finalDeadlineList.add(deadline);
            System.out.println(getMinOvershoot());
        }

    }

    private static int getMinOvershoot() {
        List<Integer> overshoots = getOvershoots(finalDurationList, finalDeadlineList);
        int maxIndex = getMaxIndex(overshoots);

        while(attemptReduction(overshoots, maxIndex)) {
            overshoots = getOvershoots(finalDurationList, finalDeadlineList);
        }

        overshoots = getOvershoots(finalDurationList, finalDeadlineList);
        return overshoots.get(getMaxIndex(overshoots));
    }

    private static boolean attemptReduction(List<Integer> resultList, int maxIndex) {

        int maxOvershoot     = resultList.get(maxIndex);
        int bestIndexToSwap  = -1;

        for(int i=0; i<resultList.size(); i++) {
            if(i != maxIndex) {
                List<Integer> modifySourceList_1 = new ArrayList<Integer>(finalDurationList);
                List<Integer> modifySourceList_2 = new ArrayList<Integer>(finalDeadlineList);

                //
                Integer swapHolder = modifySourceList_1.get(i);
                modifySourceList_1.set(i, modifySourceList_1.get(maxIndex));
                modifySourceList_1.set(maxIndex, swapHolder);

                swapHolder = modifySourceList_2.get(i);
                modifySourceList_2.set(i, modifySourceList_2.get(maxIndex));
                modifySourceList_2.set(maxIndex, swapHolder);

                List<Integer> newResultList = getOvershoots(modifySourceList_1, modifySourceList_2);
                int newMaxIndex = getMaxIndex(newResultList);

                if(newResultList.get(newMaxIndex) < maxOvershoot) {
                    maxOvershoot = newResultList.get(newMaxIndex);
                    bestIndexToSwap = i;
                }
            }
        }

        //Just swap best index
        if(bestIndexToSwap != -1) {
            Integer finalSwapholder = finalDurationList.get(bestIndexToSwap);
            finalDurationList.set(bestIndexToSwap, finalDurationList.get(maxIndex));
            finalDurationList.set(maxIndex, finalSwapholder);

            finalSwapholder = finalDeadlineList.get(bestIndexToSwap);
            finalDeadlineList.set(bestIndexToSwap, finalDeadlineList.get(maxIndex));
            finalDeadlineList.set(maxIndex, finalSwapholder);
            return true;
        } else {
            return false;
        }


    }

    private static int getMaxIndex(List<Integer> arr) {
        int maxIndex = 0;
        for(int i=0; i<arr.size(); i++) {
            if(arr.get(i) > arr.get(maxIndex)) maxIndex = i;
        }
        return maxIndex;
    }

    private static List<Integer> getOvershoots(List<Integer> durationList, List<Integer> deadlineList) {
        List<Integer> aggregateDuration = computeAggregateDuration(durationList);
        List<Integer> overshoots = computeOvershoots(aggregateDuration, deadlineList);
        return overshoots;
    }


    private static List<Integer> computeOvershoots(List<Integer> durations, List<Integer> deadlines) {
        List<Integer> overshoots = new ArrayList<Integer>();
        for(int i=0; i<durations.size(); i++) {
            Integer overshoot = durations.get(i) - deadlines.get(i);
            overshoots.add(overshoot);
        }
        return overshoots;
    }

    private static List<Integer> computeAggregateDuration(List<Integer> durations) {
        List<Integer> aggDuration = new ArrayList<Integer>();
        Integer runningTotal = 0;
        for(Integer i : durations) {
            runningTotal += i;
            aggDuration.add(runningTotal);
        }
        return aggDuration;
    }
}