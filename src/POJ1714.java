import java.util.Scanner;


public class POJ1714 {

  // 1714 WRONG ANSWER
  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);
    StringBuilder sb = new StringBuilder();
    N = s.nextInt();
    s.nextInt();
    Chamber[] chambers = new Chamber[N + 1];
    path = new int[N];
    minPath = new int[N];
    minHard = N;
    for (int i = 1; i < chambers.length; i++) {
      chambers[i] = new Chamber();
      chambers[i].id = i;
    }
    
    int e = 3 * N / 2;
    for (int i = 0; i < e; i++) {
      Edge edge = new Edge();
      edge.chamber[0] = chambers[s.nextInt()];
      edge.chamber[1] = chambers[s.nextInt()];
      edge.w=s.nextInt();
      outer:
      for (int j = 0; j < 2; j++) {
        for (int k = 0; k < 3; k++) {
          if(edge.chamber[j].edges[k] == null){
            edge.chamber[j].edges[k] = edge;
            continue outer;
          }
        }
      }
    }
    trave(chambers[1]);
    for (int i = 0; i < minPath.length; i++) {
      System.out.print(minPath[i]);
      System.out.print(' ');
    }
    System.out.print('\n');
    s.close();
  }
  
  private static int N = 0;
  public static int visited = 0;
  public static int[] path;
  public static int[] minPath;
  public static int hardCount=0;
  public static int minHard = N;
  public static void trave(Chamber c){
    path[visited] = c.id;
    visited ++;
    c.color = 1;
    for (int i = 0; i < 3; i++) {
      Edge e = c.edges[i];
      Chamber next = e.chamber[e.chamber[0].id == c.id?1:0];
      if(next.id == 1 && visited == N){
        minHard = hardCount;
        System.arraycopy(path, 0, minPath, 0, minPath.length);
      } else {
        if(next.color > 0){
          continue;
        }
        hardCount += e.w;
        if(hardCount < minHard){
          trave(next);
        }
        hardCount -= e.w;
      }
    }
    visited --;
    path[visited] = 0;
    c.color = 0;
  }

  static class Chamber{
    public int id;
    public Edge[] edges = new Edge[3];
    public int color;
  }
  static class Edge{
    public Chamber[] chamber = new Chamber[2];
    public int w;
  }
}
