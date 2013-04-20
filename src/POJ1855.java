import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;


public class POJ1855 {

  public static void main(String[] args) {

    Random r = new Random(); 
    int n = r.nextInt(46) + 4;
    int t = r.nextInt(9) + 1;
    Coin header = new Coin(0);
    Coin pre = header;
    int[] coins = new int[n];
    for (int i = 0; i < n; i++) {
      int thickness = r.nextInt(998) + 2;
      Coin c = new Coin(thickness);
      pre.next = c;
      pre = c;
      coins[i] = thickness;
    }
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < t; i++) {
      int tar = r.nextInt(10000) + 1000;
      int smallest = leg(false, header, tar);
      int greatest = smallest;
      if(smallest != tar){
        greatest = leg(true, header, tar);
      }
      int[] result = leg(coins, tar);
      sb.append(" test data = " ).append(Arrays.toString(coins)).append("  tar = ").append(tar).append('\n');
      sb.append(greatest).append(' ').append(smallest).append(" ---- method 2 ") .append(result[0]).append(' ').append(result[1]).append('\n');
    }
    System.out.println(sb);
  
  }
  // 1855
  public static void omain(String[] args) {
    Scanner s = new Scanner(System.in);
    int n = s.nextInt();
    int t = s.nextInt();
    Coin header = new Coin(0);
    Coin pre = header;
    for (int i = 0; i < n; i++) {
      Coin c = new Coin(s.nextInt());
      pre.next = c;
      pre = c;
    }
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < t; i++) {
      int tar = s.nextInt();
      int smallest = leg(false, header, tar);
      int greatest = smallest;
      if(smallest != tar){
        greatest = leg(true, header, tar);
      }
      sb.append(greatest).append(' ').append(smallest).append('\n');
    }
    System.out.println(sb);
    s.close();
  }
  
  public static int leg(boolean greatest, Coin coins, int target){
    Coin sorted = new Coin(0);
    sorted.height = greatest?0:Integer.MAX_VALUE;
    
    Coin cur = coins.next;
    while(cur != null){
      cur.height = cur.thickness * (target / cur.thickness);
      if(cur.height < target && !greatest){
        cur.height += cur.thickness;
      }
      Coin next = cur.next;
      cur.next = null;
      insert(greatest, sorted, cur);
      cur = next;
    }

    while(!check(sorted)){
      cur = sorted.next;
      sorted.next = cur.next;
      cur.next = null;
      cur.height = cur.thickness * (greatest?-1:1)  + cur.height;
      if(cur.height < 0){
        continue;
      }
      insert(greatest, sorted, cur);
    }
    coins.next = sorted.next;
    return sorted.next.height;
  }
  
  public static void insert(boolean greatest, Coin sorted, Coin coin){
    coin.next = null;
    Coin cur = sorted;
    while(true){
      if(cur.next == null){
        cur.next = coin;
        break;
      }
      if(((cur.next.height > coin.height) ^ greatest) && (cur.next.height != coin.height)){
        coin.next = cur.next;
        cur.next = coin;
        break;
      }
      cur = cur.next;
    }
  }
  
  public static boolean check(Coin sorted){
    if(sorted.next.height == sorted.next.next.height && sorted.next.height == sorted.next.next.next.height && sorted.next.height == sorted.next.next.next.next.height){
      return true;
    }
    return false;
  }

  static class Coin{
    public int thickness;
    public Coin(int thickness) {
      super();
      this.thickness = thickness;
    }
    public int height;
    public Coin next;
  }
  
  
  public static int[] leg(int[] coins, int tar){
    int g, s;
    g = 0;
    s = Integer.MAX_VALUE;
    for (int i = 0; i < coins.length; i++) {
      for (int j = i + 1; j < coins.length; j++) {
        for (int k = j + 1; k < coins.length; k++) {
          for (int l = k + 1; l < coins.length; l++) {
            int a = coins[i] * coins[j] /gcd(coins[i], coins[j]);
            int b = coins[k] * coins[l]/ gcd(coins[k], coins[l]);
            int c = a * b / gcd(a,b);
            int d = c * (tar / c);
            if(d == tar){
              g = s = d;
              return new int[] {g,s};
            } else {
              if(d > g){
                g = d;
              }
              if((d + c) < s){
                s = d + c;
              }
            }
          }
        }
      }
    }
    return new int[]{g,s};
  }
  
  public static int gcd(int a, int b){
    int r = a % b;
    while(r != 0){
      a = b;
      b = r;
      r = a % b;
    }
    return b;
  }
}
