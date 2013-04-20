import java.util.Scanner;

public class POJ1579 {

  public static void main(String[] args) {
    StringBuilder sb = new StringBuilder();
    Scanner s = new Scanner(System.in);
    while (s.hasNextInt()) {
      int n1 = s.nextInt();
      int n2 = s.nextInt();
      int n3 = s.nextInt();
      if (n1 == -1 && n2 == -1 && n3 == -1) {
        break;
      }
      sb.append("w(" + n1 +", " + n2 +", " + n3 +") = " + w(n1,n2,n3) + "\n");
    }
    System.out.println(sb);
  }

  private static int[][][] wArr = new int[21][21][21];

  public static int w(int a, int b, int c) {
    if (a <= 0 || b <= 0 || c <= 0) {
      return 1;
    } else if (a > 20 || b > 20 || c > 20) {
      return w(20, 20, 20);
    } else if (wArr[a][b][c] != 0) {
      return wArr[a][b][c];
    } else {
      if (a < b && b < c) {
        wArr[a][b][c] = w(a, b, c - 1) + w(a, b - 1, c - 1) - w(a, b - 1, c);
      } else {
        wArr[a][b][c] = w(a - 1, b, c) + w(a - 1, b - 1, c) + w(a - 1, b, c - 1) - w(a - 1, b - 1, c - 1);
      }
      return wArr[a][b][c];
    }
  }

}
