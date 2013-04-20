import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class POJ1948 {
  // 1948 Accepted  3272K 2032MS
  public static void gen(){
    Random r = new Random();
    int n = r.nextInt(38) + 3;
    System.out.println(n);
    int[] ar = new int[n];
    for (int i = 0; i < n; i++) {
      ar[i] = r.nextInt(40) + 1;
    }
    Arrays.sort(ar);
    for (int i = n - 1; i >=0; i--) {
      System.out.println(ar[i]);
    }
  }
  // 1948
  public static void main(String[] args) {
    if(args.length > 0){
      gen();
      return;
    }
    Scanner s = new Scanner(System.in);
    int n = s.nextInt();
    rails = new int[n];
    length = 0;
    for (int i = 0; i < n; i++) {
      rails[i]=s.nextInt();
      length += rails[i];
    }
    int half = rails.length / 2;
    Arrays.sort(rails);
    for (int i = 0; i < half ; i++) {
      int tmp = rails[i];
      rails[i] = rails[rails.length - i - 1];
      rails[rails.length - i - 1] = tmp;
    }
    upBound = length / 2;
    maxArea = -1;
    dp = new boolean[upBound + 1][upBound + 1];
    System.out.println(triangular());
    s.close();
  }

  public static boolean[][] dp;
  
  public static int[] rails;
  public static int length;
  public static int upBound;
  public static double maxArea;
  public static int triangular(){
    double p = length/2d;
    dp[0][0] = true;
    boolean noResult = true;
    for (int i = 0; i < rails.length; i++) {
      for (int j = upBound; j >= 0 ; j--) {
        for (int k = j; k >= 0 ; k--) {
          if(j >= rails[i]){
            dp[j][k] |= dp[j - rails[i]][k];
          }
          if(k >= rails[i]){
            dp[j][k] |= dp[j][k-rails[i]];
          }
          if(dp[j][k]){
          }
        }
      }
    }
    for (int j = upBound; j >= 0 ; j--) {
      for (int k = j; k >= 0 ; k--) {
        if(dp[j][k]){
          int l = length - j - k;
          if(j + k > l && j + l > k && k + l > j){
            noResult = false;
            maxArea = Math.max(maxArea, Math.sqrt(p*(p-j)*(p-k)*(p-l)));
          }
        }
      }
    }
    if(noResult){
      return -1;
    } else {
      return (int)(maxArea*100);
    }
  }
}
