import java.util.HashMap;
import java.util.Scanner;


public class POJ1826 {
  //   1826
  // method 1
  static int[][] sq ;
  public static void main(String[] args) {

    Scanner s = new Scanner(System.in);
    StringBuilder sb = new StringBuilder();
    while(true) {
      int n = s.nextInt();
      if(n == 0) break;
      sq = new int[n][3];
      for (int j = 0; j < n; j++) {
        sq[j][0] = s.nextInt();
        sq[j][1] = s.nextInt();
        sq[j][2] = s.nextInt();
      }
      connect();
      sb.append(maxArea).append('\n');
    }
    System.out.println(sb);
  }
  public static int maxArea = 0;
  private static int lastIdx;
  private static void connect() {
    maxArea = 0;
    lastIdx = sq.length - 1;
    while(lastIdx >= 0){
      int a = sum(sq[0][0],sq[0][1]);
      if( a > maxArea) maxArea = a;
    }
  }
  
  private static int sum(int x, int y) {
    int a = 0; 
    for (int i = 0; i <= lastIdx; i++) {
      if(x == sq[i][0] && y == sq[i][1]){
        a = sq[i][2];
        sq[i] = sq[lastIdx--];
      }
    }
    if(a == 0) return 0;
    a += sum(x+1,y) + sum(x-1,y) + sum(x,y+1) +sum(x,y-1);
    return a;
  }


  
/*  
  // method 2
  static HashMap<Square, Integer> squ ;
  public static void main(String[] args) {

    Scanner s = new Scanner(System.in);
    StringBuilder sb = new StringBuilder();
    while(true) {
      int n = s.nextInt();
      if(n == 0) break;
      squ = new HashMap<Square, Integer>(n);
      for (int j = 0; j < n; j++) {
        squ.put(new Square(s.nextInt(),s.nextInt()), s.nextInt());
      }
      connect();
      sb.append(maxArea).append('\n');
    }
    System.out.println(sb);
  }
  public static int maxArea = 0;
  private static void connect() {
    maxArea = 0;
    
    while(!squ.isEmpty()){
      Square s = squ.keySet().iterator().next();
      int a = sum(s);
      if( a > maxArea) maxArea = a;
    }
  }
  
  private static int sum(Square s) {
    if(s == null){
      return 0;
    }
    Integer intA = squ.remove(s);
    if(intA == null){
      return 0;
    }
    int a = intA.intValue();
    Square nextS = new Square(s.x+1, s.y);
    a += sum(nextS);
    nextS.x=s.x-1;
    a += sum(nextS);
    nextS.x=s.x;
    nextS.y=s.y+1;
    a += sum(nextS);
    nextS.y=s.y-1;
    a += sum(nextS);
    return a;
  }
  static class Square{

    public Square(int x, int y) {
      this.x = x;
      this.y = y;
    }
    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + x;
      result = prime * result + y;
      return result;
    }
    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      Square other = (Square) obj;
      if (x != other.x)
        return false;
      if (y != other.y)
        return false;
      return true;
    }
    int x;
    int y;
    
  }
  */
}
