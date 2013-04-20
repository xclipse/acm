import java.util.ArrayList;
import java.util.Scanner;

public class POJ1924_YYGY {

  // 1924 
  public static final int MAX = 104;
  public static boolean boundary(int xy, int upBoundary){
    return xy >=0 && xy < upBoundary;
  }
  public static void main(String[] args) {
    
    Scanner s = new Scanner(System.in);
    StringBuilder sb = new StringBuilder();
    while (true) {
      
      byte r,c;
      r = s.nextByte();
      c = s.nextByte();
      if(r == 0){
        break;
      }
      byte px = 0,py = 0, ty = 0, tx = 0;
      field = new byte[MAX][MAX];
      visited = new boolean[MAX][MAX][MAX];
      ArrayList<Byte> monType = new ArrayList<Byte>();
      for (byte y = 0; y < r; y++) {
        char[] row = s.next().toCharArray();
        for (byte x = 0; x < row.length; x++) {
          byte type = 0;
          switch (row[x]) {
          case '#':
            type = ROCK;
            break;
          case 't':
            ty = y;
            tx = x;
            break;
          case 'p':
            py = y;
            px = x;
           break;
          case 'a':
            monType.add(AGG_MON);
            break;
          case 'n':
            monType.add(NON_AGG_MON);
            break;
          }
          field[y][x]= type;
        }
      }
      int monNum = s.nextInt();
      mon.clear();
      for (int i = 0; i < monNum; i++) {
        byte[][] steps = new byte[s.nextByte()][];
        
        for (int time = 0; time < steps.length; time++) {
          steps[time] = new byte[]{(byte) (s.nextByte() - 1),  (byte) (s.nextByte() - 1),  monType.get(i)};
        }
        mon.add(steps);
      }
      int result = search(py,px,ty,tx, r, c);
      if(result > 0){
        sb.append(result).append('\n').append('\n');
      } else {
        sb.append("impossible").append('\n').append('\n');
      }
    }
    System.out.println(sb);
    s.close();
    

  }

  public static ArrayList<byte[][]> mon = new ArrayList<byte[][]>();
  public static byte [][] field;
  public final static byte ROCK=1,AGG_MON=2,NON_AGG_MON=3;
  public final static byte TYPE_MASK = 0x0f; 
  public final static byte STATUS_MASK = 0x70;
  public static boolean[][][] visited;

  public final static int queueMaxSize = 500000;
  public static byte[][] q = new byte[queueMaxSize][2];
  public static int h = 0,t = 0, queueSize = 0;
  public static void inQueue(byte y, byte x){
    queueSize++;
    if(queueSize > queueMaxSize) throw new RuntimeException();
    q[t][0] = y;
    q[t][1] = x;
    t = (t + 1) % queueMaxSize;
  }
  public static byte[] deQueue(){
    if(queueSize == 0){
      return null;
    }
    queueSize --;
    byte[] b = q[h];
    h = (h + 1) % queueMaxSize;
    return b;
  }
  public static boolean empty(){
    return queueSize == 0;
  }
  public static byte[][] dir = new byte[][]{
    {1,0},{1,1},{0,1},{-1,1},{-1,0},{-1,-1},{0,-1},{1,-1}};
  public static byte search(byte y, byte x, byte ty, byte tx, byte r, byte c){
    queueSize = h = t = 0;
    byte time = 0;
    inQueue(y,x);
    while (!empty() && time <= 100){
      time++;
      int size = queueSize;
      setMon(time, -1, r, c);
      for (int i = 0; i < size; i++) {
        byte[] cur = deQueue();
        for (byte tranIdx = 0; tranIdx < dir.length; tranIdx++) {
          // try to move 2 step in one direction
          for (byte repeatMove = 0; repeatMove < 2; repeatMove++) {
            byte my = (byte) (cur[0] + dir[tranIdx][0] * (repeatMove + 1));
            byte mx = (byte) (cur[1] + dir[tranIdx][1] * (repeatMove + 1));
            if(!boundary(mx, c) || !boundary(my, r)){
              continue;
            }            
            if(my == ty && mx == tx){
              return time;
            }
            if(!visited[my][mx][time] && field[my][mx] == 0){
              visited[my][mx][time] = true;
              inQueue(my,mx);
            } else {
              // failed to move any more step in this direction
              break;
            }
          }
        }
        if(!visited[cur[0]][cur[1]][time]){
          inQueue(cur[0], cur[1]);
        }

      }
      setMon(time,1, r, c);
    }
    return -1;
  }
  private static void setMon(int time, int remove, int r, int c) {
      for (byte[][] bs : mon) {
        byte[] monPoint = bs[time%bs.length];
        
        field[monPoint[0]][monPoint[1]] += monPoint[2] * (-1 * remove);
        if(monPoint[2] == AGG_MON){
          for (int i = 0; i < dir.length; i++) {
            int my = monPoint[0] + dir[i][0];
            int mx = monPoint[1] + dir[i][1];
            if(!boundary(mx, c) || !boundary(my, r)){
              continue;
            }
            field[my][mx] += monPoint[2] * (-1 * remove);
          }
        }
      }
  }
}
