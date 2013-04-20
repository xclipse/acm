import java.util.Scanner;

public class POJ1980 {

  /*
2 3 120 3
2 3 300 3
2 3 299 3
2 3 12 3
2 3 12000 7
54 795 12000 7
2 3 300 1
2 1 200 5
2 4 54 2
0 0 0 0
   */
  // 1980 Accepted  4176K 2094MS
  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);

    int p = s.nextInt();
    int q = s.nextInt();
    int a = s.nextInt();
    int n = s.nextInt();
    while (p != 0) {
      System.out.println(unit(p, q, 1, a, n));
      p = s.nextInt();
      q = s.nextInt();
      a = s.nextInt();
      n = s.nextInt();
    }
    s.close();

  }

  public static void sub(int p1, int q1, int p2, int q2, int[] result) {
    int p = p1 * q2 - p2 * q1;
    int q = q1 * q2;
    if (p < 0) {
      result[0] = -1;
      result[1] = -1;
      return;
    }
    int gcd = egcd(p, q);
    result[0] = p / gcd;
    result[1] = q / gcd;
  }

  public static int egcd(int a, int b) {
    int m = a % b;
    while (m != 0) {
      a = b;
      b = m;
      m = a % b;
    }
    return b;
  }

  public static int unit(int p, int q, int curUnit, double a, int n) {
    
    if (n == 1) {
      if (p != 1 || q > a || q < curUnit) {
        return 0;
      } else {
        return 1;
      }
    }
    int unit = curUnit;
    double tar = (double)p / (double)q;
    for (; (1d/unit) > tar ; unit++);
    if (unit > a) {
      return 0;
    }

    int count = 0;
    int[] result = new int[2];
    int ceil = (int) Math.ceil(Math.min(((double)n * q) / (double)p, Math.max(q, Math.sqrt(a))));
    for (; unit <= ceil; unit++) {
      sub(p, q, 1, unit, result);
      if (result[0] < 0) {
        continue;
      } else if (result[0] == 0) {

        count++;
        continue;
      }
      count += unit(result[0], result[1], unit, a / unit, n - 1);
    }
    return count;
  }
}
