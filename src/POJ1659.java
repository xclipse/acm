import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

//Accepted  3372K 141MS
public class POJ1659 {

  private static int[][] matrix;
  private static int[] links;
  private static int n;

  public static void main(String[] args) throws IOException {
    StringBuilder sb = new StringBuilder();
    Scanner s = new Scanner(System.in);
    int count = s.nextInt();
    for (int i = 0; i < count; i++) {
      n = s.nextInt();
      matrix = new int[n][n];
      links = new int[n];
      for (int j = 0; j < n; j++) {
        links[j] = s.nextInt();
      }
      boolean r = test(1, 0);
      if (r) {
        sb.append("YES\n");
        for (int j = 0; j < n; j++) {
          for (int k = 0; k < n; k++) {
            sb.append(matrix[j][k]).append(' ');
          }
          sb.append('\n');
        }
        sb.append('\n');
      }else{
        sb.append("NO\n\n");
      }
    }
    System.out.println(sb);
    s.close();
  }

  public static boolean test(int x, int y) {
    boolean r = false;
    // fill in 0 for (x,y);
    int rest = n - x - 1;
    if (links[y] <= rest) {
      r = check(x, y);
    }
    if (r)
      return r;
    // fill in 1 for (x,y)
    matrix[x][y] = matrix[y][x] = 1;
    links[y]--;
    links[x]--;
    if (links[y] >= 0 && links[x] >= 0 && links[y] <= rest) {
      r = check(x, y);
    }
    if (!r) {
      matrix[x][y] = matrix[y][x] = 0;
      links[y]++;
      links[x]++;
    }
    return r;
  }

  private static boolean check(int x, int y) {
    boolean r;
    int rest = n - x - 1;
    if (rest == 0) {
      if (y == n - 2) {
        for (int i = 0; i < links.length; i++) {
          if (links[i] != 0) {
            return false;
          }
        }
        return true;
      } else {
        r = test(y + 2, y + 1);
        if (r)
          return r;
      }
    } else {
      r = test(x + 1, y);
      if (r)
        return r;
    }
    return false;
  }
}
