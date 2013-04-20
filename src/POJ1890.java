import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;


public class POJ1890 {

  // 1890 Accepted  4572K 922MS
  public static void main(String[] args) {

    int A;
    
    Scanner s = new Scanner(System.in);
    StringBuilder sb = new StringBuilder();
    int ds = 1;
    while(true){
      N = s.nextInt();
      if(N == 0){
        break;
      }
      prog = new int[N];
      minSche = new int[N];
      sche = new int[N];
      for (int i = 0; i < N; i++) {
        prog[i] = s.nextInt();
      }
      Arrays.sort(prog);
      A = s.nextInt();
      align = new int[A][2];
      for (int i = 0; i < A; i++) {
        align[i][0] = s.nextInt() - 1;
        align[i][1] = s.nextInt();
      }
      Arrays.sort(align, new Comparator<int[]>() {

        public int compare(int[] o1, int[] o2) {
          if(o1[1] > o2[1]){
            return 1;
          } else if(o1[1] < o2[1]){
            return -1;
          }
          return 0;
        }
      });
      int maxLevel = 0;
      for (int i = 0; i < align.length; i++) {
        if(maxLevel < align[i][0]){
          maxLevel = align[i][0];
        }
      }
      error = new int[maxLevel + 1];
      minError = new int[maxLevel + 1];
      for (int i = 0; i < align.length; i++) {
        if(minError[align[i][0]] < align[i][1]){
          minError[align[i][0]] = align[i][1];
        }
      }
      pick();
      sb.append("Data set ").append(ds++);
      sb.append("\nOrder:");
      for (int i = 0; i < minSche.length; i++) {
        sb.append(' ').append(minSche[i]);
        
      }
      int errorSum = 0;
      for (int i = 0; i < minError.length; i++) {
        errorSum += minError[i];
      }
      sb.append("\nError: ").append(errorSum).append('\n');
    }
   
    System.out.println(sb);
    s.close();
  }

  public static int [] prog,error,minError,minSche,sche;
  public static int [][] align;
  public static int N, sIdx;
  
  public static void pick(){
    int sum;
    int curProg =  -1;
    for (int i = 0; i < N; i++) {
      if(prog[i] != 0){
        curProg =  prog[i];
        prog[i] = 0;
        sche[sIdx++]=curProg;
        pick();
        sche[--sIdx]=0;
        prog[i] = curProg;
      }
    }
    if(curProg < 0){
      sum = 0;
      int lastSum, lastIdx = 0;
      Arrays.fill(error, 0);
      for (int i = 0; i < N; i++) {
        lastSum = sum;
        sum = sum + sche[i];
        for (int idx = lastIdx; idx < align.length; idx++) {
          if(align[idx][1] <= sum){
            error[align[idx][0]] += Math.min(align[idx][1] - lastSum, sum - align[idx][1]);
            lastIdx = idx + 1;
          } else {
            lastIdx = idx;
            break;
          }
        }
      }
      
      for (int i = 0; i < error.length; i++) {
        if(minError[i] > error[i]){
          System.arraycopy(error, 0, minError, 0, error.length);
          System.arraycopy(sche, 0, minSche, 0, minSche.length);
        } else if(minError[i] < error[i]){
          return;
        }
      }
    }
  }
  
}
