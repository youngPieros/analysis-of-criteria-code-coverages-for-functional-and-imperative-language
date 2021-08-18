import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

/*
 * Constraints:
1 <= T <= 100000
1 <= Di <= 100000
1 <= Mi <= 1000
Sample Input:
5
2 2
1 1
4 3
10 1
2 1
 */
public class Main5 {
    public static int MAX = 100001;
    public static int[] _timeSlots = new int[100001];
    public static int marker = 0;
    public static int overShoot = 0;
    public static int scheduleTask(int t_i, int d){
        int d_i = d;

        if(d_i <= marker){
            overShoot += t_i;
            return overShoot;
        }
        int j = d_i;
        while(j> marker && j> 0 && t_i>0){
            if(_timeSlots[j] > 0){
                j-=1;
                continue;
            }else{
                _timeSlots[j]= 1;
                t_i -=1;
                j-=1;
            }
        }
        if(t_i!=0){
            overShoot += t_i;
            marker = Math.max(d,marker);
        }

        return overShoot;
    }
    public static void main(String args[]){
        Scanner scan = null;
        scan = new Scanner(new InputStreamReader(System.in));
		/*
		try {
			scan = new Scanner(new FileReader("taskscheduling.in"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        int t = scan.nextInt();
        int t_i,d_i;
        while(t>0){
            t -=  1;
            d_i = scan.nextInt();
            t_i = scan.nextInt();
            System.out.println(scheduleTask(t_i, d_i));
        }
    }

}