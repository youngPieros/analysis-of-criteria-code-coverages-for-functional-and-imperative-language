package BatchScheduling.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main8 {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int numberOfRecords = Integer.parseInt(br.readLine());

        int[][] inputArray = new int[numberOfRecords][2];
        for(int i=0; i<numberOfRecords; i++){
            String input =br.readLine();
            String[] inputs = input.split(" ");
            int deadLine = Integer.parseInt(inputs[0]);
            int timeRequired = Integer.parseInt(inputs[1]);

            inputArray[i][0] = deadLine - timeRequired;
            inputArray[i][1] = timeRequired;
        }

        //System.out.println(0);

        for(int i = 0 ; i < numberOfRecords; i++){
            int[][] inArray = new int[i+1][2];
            for(int j=0; j<i+1;j++){
                inArray[j][0] = inputArray[j][0];
                inArray[j][1] = inputArray[j][1];
            }
            System.out.println(doScheduling(inArray,i+1));
        }
    }

    private static int doScheduling(int[][] inputArray,int noOfJobs){
        int returnValue = 0;
        while(noOfJobs > 1){
            int jobToBeProcessed=0;
            for(int i =0; i<noOfJobs; i++){
                int k = inputArray[i][0];
                k-=1;
                inputArray[i][0] = k;
                if(inputArray[jobToBeProcessed][0] > inputArray[i][0]){
                    jobToBeProcessed = i;
                }
            }
            int processingRemaning = inputArray[jobToBeProcessed][1];
            if(processingRemaning == 1){
                noOfJobs--;
                if(inputArray[jobToBeProcessed][0] + 1 < returnValue){
                    returnValue = inputArray[jobToBeProcessed][0] + 1;
                }
                inputArray[jobToBeProcessed][0] = inputArray[noOfJobs][0];
                inputArray[jobToBeProcessed][1] = inputArray[noOfJobs][1];
            }else{
                inputArray[jobToBeProcessed][1] = processingRemaning - 1;
                inputArray[jobToBeProcessed][0] = inputArray[jobToBeProcessed][0] + 1;
            }
        }

        if(inputArray[0][0] < returnValue){
            returnValue = inputArray[0][0];
        }

        if(returnValue == 0)
            return returnValue;
        else
            return -1*(returnValue);
    }
}