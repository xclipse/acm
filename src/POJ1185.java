import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class POJ1185 {
  public static void main(String[] args) {
    
  }
  
  public static int[] status = new int[70];
  public static int boundare;
  public static int[] statusNumber = new int[70];
}
/*
public class POJ1185 {

  public static int[] pre;
  public static int[] ppre;
  public static int[] cur;
  public static int[] curp;
  public static boolean [][] map;
  public static int N,M;
  public static int[] selfCheck;
  
  public static void gen(){
    Random r = new Random();
    int n = r.nextInt(10) + 1;
    int m = r.nextInt(10)+1;
    System.out.print(n);
    System.out.print(' ');
    System.out.println(m);
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        if(r.nextBoolean()){
          System.out.print('H');
        } else {
          System.out.print('P');
        }
      }
      System.out.println();
    }
  }
  
  public static void main(String[] args) {
    //gen();
    Scanner s = new Scanner(System.in);
    N = s.nextInt();
    M = s.nextInt();
    pre = new int[1<<M];
    ppre = new int[1<<M];
    cur = new int[1<<M];
    curp = new int[1<<M];
    selfCheck = new int[1 << M];
    map = new boolean[N][M];
    for (int i = 0; i < N; i++) {
      char[] row = s.next().toCharArray();
      for (int j = 0; j < row.length; j++) {
        map[i][j]= row[j] == 'H';
      }
    }
    s.close();
    System.out.println(statusDP());
  }

  public static int statusDP(){
    int lineFull = 1 << M;
    int[] swap;
    for (int row = 0; row < N; row++) {
      System.out.println("row = " + row );
      for (int i = 0; i < lineFull; i++) {
        selfCheck[i] = selfCheck(i, row);
      }
      
      for (int i = 0; i < lineFull; i++) {
        if(pre[i] < 0){
          continue;
        }
        for (int j = 0; j < lineFull; j++) {
          int oneCount = selfCheck[j];
          int tmpTotal = oneCount + pre[i];
          if(oneCount != -1){
            if(oneCount > cur[j]){
              System.out.println(Integer.toBinaryString(j) + " oneCount = " + oneCount);
              cur[j] = oneCount;
              curp[j] = i;
            }
            if(tmpTotal <= cur[j] || !check(j,i,ppre[i])){
              continue;
            }
            System.out.println(Integer.toBinaryString(j) + " tmpTotal = " + tmpTotal);
            cur[j] = tmpTotal;
            curp[j] = i;
          } else {
            cur[j] = -1;
            curp[j] = -1;
          }
        }
      }
      swap = cur;
      cur = pre;
      pre = swap;
      swap = curp;
      curp = ppre;
      ppre = swap;
    }
    int max = 0;
    for (int i = 0; i < lineFull; i++) {
      if(max < pre[i]){
        max = pre[i];
      }
    }
    return max;
  }

  private static boolean check(int status, int pStatus, int ppStatus) {
    for (int i = 0; i < M; i++) {
      if(((status >> i) & 1)== 1 && ((((pStatus >> i) & 1)== 1) || (((ppStatus >> i) & 1)== 1))){
        return false;
      }
    }
    return true;
  }

  private static int selfCheck(int status, int row) {
    int count = 0;
    boolean[] field = Arrays.copyOf(map[row],M);
    for (int i = 0; i < M; i++) {
      if(((status >> i) & 1)== 1){
        count++;
        if(field[i]){
          return -1;
        }
        for (int j = 1; j <= 2; j++) {
          if(i + j < M){
            field[i + j] = true;
          }
        }
      }
    }
    return count;
  }
}
*/