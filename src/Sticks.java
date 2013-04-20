import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


class Sticks {

  public static void main(String[] args) throws IOException {
    Scanner s = new Scanner(System.in);
    ArrayList<Sticks> list = new ArrayList<Sticks>();
    while(s.hasNextInt()){
      int n = s.nextInt();
      if(n != 0){
        int[] sticks = new int[n];
        for (int i = 0; i < n; i++) {
          sticks[i] = s.nextInt();
        }
        list.add(new Sticks(sticks));
      } else {
        break;
      }
    }
    for (Sticks sticks : list) {
      System.out.println(sticks.minSticks());
    }
  }
  
  
  private int[] sticks, flags;
  private int target;
  private int total;
  
  public Sticks(int[] s) {
    sticks = s;
  }
  public int minSticks(){
    Arrays.sort(sticks);
    flags = new int[sticks.length];
    Arrays.fill(flags, 0);
    int i = 0;
    for (i = 0; i < sticks.length; i++) {
      total += sticks[i];
    }
    for (target = sticks[sticks.length-1]; target < total; target++) {
      if(total%target != 0) continue;
      if(make(target,total/target - 1)){
        return target;
      }
    }
    return total;
  }

  private boolean make(int rest, int n) {
    for (int i = 0; i < sticks.length; i++) {
      if(flags[i] != 0) continue;
      if(sticks[i] < rest){
        flags[i] = n + 1;
        if(make(rest - sticks[i], n)){
          return true;
        } else {
          flags[i] = 0;
        }
      } else if(sticks[i] == rest){
        flags[i] = n + 1;
        if(n == 0){
          return true;
        }
        if(make(target, n - 1)){
          return true;
        } else{
          flags[i] = 0;
        }
      } else {
        return false;
      }
    }
    return false;
  }
}
