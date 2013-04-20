import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeSet;

// Accepted  3140K 125MS
public class POJ1771 {

  public static void main(String[] args) {

    int[] floor;
    StringBuilder sb = new StringBuilder();
    Scanner s = new Scanner(System.in);
    int n;
    while ((n = s.nextInt()) != 0) {
      floor = new int[n + 1];
      floor[0] = 0;
      for (int i = 1; i <= n; i++) {
        floor[i] = s.nextInt();
      }
      boolean[] bFloor = new boolean[floor[floor.length - 1] + 1];
      for (int i = 0; i < floor.length; i++) {
        bFloor[floor[i]] = true;
      }
      int min, max, mid;
      min = 0;
      max = (n - 1) * 10 + floor[n] * 4;
      mid = (max - min) / 2;
      stopPlan(384, bFloor, null);
      while (true) {
        if (stopPlan(mid, bFloor, null)) {
          max = mid;
        } else {
          min = mid;
        }
        mid = (max - min) / 2;
        if (mid == 0) {
          int[] result = new int[n + 1];
          stopPlan(max, bFloor, result);
          int stopCount = 0;
          for (int j = 0; j < result.length && result[j] != 0; j++) {
            stopCount++;
          }
          sb.append(max).append('\n').append(stopCount).append(' ');
          for (int j = 0; j < stopCount; j++) {
            sb.append(result[j]).append(' ');
          }
          break;
        }
        mid += min;
      }
      sb.append('\n');
    }
    System.out.println(sb);
    s.close();
  }

  public static boolean stopPlan(int maxTime, boolean[] floor, int[] planArray) {
    int stopCount = 0;
    int curStop = 0;
    int i = maxTime / 20 + 2;
    int topFloor = floor.length - 1;
    while (i <= topFloor) {
      while (i <= topFloor && floor[i] == false) {
        i++;
      }
      if ((i - 1) * 4 + 10 * stopCount > maxTime) {
        return false;
      }

      curStop = (maxTime + 20 * i - 10 * stopCount + 4) / 24;
      i = (maxTime + 16 * curStop - 10 * stopCount + 4) / 20 + 1;
      if (planArray != null) {
        planArray[stopCount] = curStop;
      }
      stopCount++;
    }
    return true;
  }

}
