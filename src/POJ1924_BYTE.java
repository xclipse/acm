import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;


public class POJ1924_BYTE {

  // 1924 

  public static String printField(int time){
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < field.length; i++) {
      for (int j = 0; j < field[i].length; j++) {
        switch(field[i][j][time%field[i][j].length]){
        case (ROCK + BLACK):
          sb.append('#');
          break;
        case (AGG_MON + BLACK):
          sb.append('a');
          break;
        case (NON_AGG_MON + BLACK):
          sb.append('n');
        break;
        case (PLAYER):
          sb.append('P');
        break;
        case (TARGET):
          sb.append('t');
        break;
        default:
          sb.append('.');
        }
      }
      sb.append('\n');
    }
    return sb.toString();
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
      byte px = 0,py = 0;
      field = new byte[r + 4][c + 4][];

      byte[] rock = new byte[]{ROCK + BLACK};
      for (int i = 0; i < r + 4; i++) {
        field[i][0] = field[i][1] = field[i][c+2] =field[i][c+3] = rock;
      }
      for (int j = 0; j < c + 4; j++) {
        field[0][j] = field[1][j] = field[r+2][j] = field[r+3][j] = rock;
      }
      for (byte i = 0; i < r; i++) {
        char[] row = s.next().toCharArray();
        for (byte j = 0; j < row.length; j++) {
          byte y = (byte) (i + 2);
          byte x = (byte) (j + 2);
          byte type = WHITE;
          switch (row[j]) {
          case '#':
            type = ROCK + BLACK;
            break;
          case 't':
            type = TARGET;
            break;
          case 'p':
            py = y;
            px = x;
            type = PLAYER;
            break;
          case 'a':
            type = AGG_MON + BLACK;
            break;
          case 'n':
            type = NON_AGG_MON + BLACK;
            break;
          }
          field[y][x]= new byte[]{type};
        }
      }
      int monNum = s.nextInt();
      for (int i = 0; i < monNum; i++) {
        byte[][] steps = new byte[s.nextByte()][];

        
        for (int time = 0; time < steps.length; time++) {
          steps[time] = new byte[]{ (byte) (s.nextByte() + 1),  (byte) (s.nextByte() + 1) };
        }
        boolean isAgg = (field[steps[0][0]][steps[0][1]][0] & TYPE_MASK) == AGG_MON;
        for (byte time = 0; time < steps.length; time++) {
          byte monY = steps[time][0]; 
          byte monX = steps[time][1]; 
          updateBlock(steps, isAgg, time, monY, monX);
          if(isAgg){
            for (int tranIdx = 0; tranIdx < tranNum.length; tranIdx++) {
              updateBlock(steps, isAgg, time, (byte) (monY + tranNum[tranIdx][0]) ,(byte) ( monX + tranNum[tranIdx][1]));
            }
          }
        }
      }
      System.out.println(printField(0));
      System.out.println(printField(1));
      System.out.println(printField(2));
      int result = search(py,px);
      if(result > 0){
        sb.append(result).append('\n').append('\n');
      } else {
        sb.append("impossible").append('\n').append('\n');
      }
    }
    System.out.println(sb);
    s.close();
  }
  private static void updateBlock(byte[][] steps, boolean isAgg, byte time, byte monY, byte monX) {
    byte[] curBlock = field[monY][monX];
    byte type = (byte) (curBlock[0] & TYPE_MASK);
    if(type == ROCK || type == PLAYER || type == TARGET){
      return;
    }
    if(curBlock.length < steps.length){
      curBlock = Arrays.copyOf(curBlock, steps.length);
      field[monY][monX] = curBlock;
    }
    if((curBlock[time] & STATUS_MASK) == WHITE){
      curBlock[time] = (byte) ((isAgg?AGG_MON:NON_AGG_MON) + BLACK);
    }
  }

  public static byte [][][] field;
  public final static byte ROCK=1,AGG_MON=2,NON_AGG_MON=3,PLAYER=4,TARGET=5,WHITE=0,GRAY=32,BLACK=64;
  public final static byte TYPE_MASK = 0x0f; 
  public final static byte STATUS_MASK = 0x70;
  

  public static byte[][] tranNum = new byte[][]{
    {1,0},{1,1},{0,1},{-1,1},{-1,0},{-1,-1},{0,-1},{1,-1}};
  public static byte search(byte y, byte x){
    byte time = 0;
    LinkedList<byte[]> q = new LinkedList<byte[]>();
    q.add(new byte[]{y,x,time});
    field[y][x][0] += PLAYER;
    while (!q.isEmpty() && time <= 100){
      byte[] cur = q.removeFirst();
      time = (byte) (cur[2] + 1);
      for (byte tranIdx = 0; tranIdx < tranNum.length; tranIdx++) {
        // try to move 2 step in one direction
        for (byte repeatMove = 0; repeatMove < 2; repeatMove++) {
          byte my = (byte) (cur[0] + tranNum[tranIdx][0] * (repeatMove + 1));
          byte mx = (byte) (cur[1] + tranNum[tranIdx][1] * (repeatMove + 1));
          int modTime = time%field[my][mx].length;
          byte tarBlockStatus = field[my][mx][modTime];
          if((tarBlockStatus & TYPE_MASK) == TARGET){
            return time;
          }
          if((tarBlockStatus & STATUS_MASK) == WHITE){
            tarBlockStatus = (byte) ((tarBlockStatus & TYPE_MASK) + GRAY);
            field[my][mx][modTime] = tarBlockStatus;
            q.add(new byte[]{my,mx,time});
          } else {
            // failed to move any more step in this direction
            break;
          }
        }
      }
      // check is it necessary to stay on the same block for one more round
      for (int tranIdx = 0; tranIdx < tranNum.length; tranIdx++) {
        int my = cur[0] + tranNum[tranIdx][0];
        int mx = cur[1] + tranNum[tranIdx][1];
        for (int i = 0; i < field[my][mx].length; i++) {
          if((field[my][mx][i] & STATUS_MASK) == WHITE ){
            q.add(new byte[]{cur[0], cur[1], time});
            break;
          }
        }
      }
      byte modTime = (byte) (cur[2]%field[cur[0]][cur[1]].length);
      field[cur[0]][cur[1]][modTime] =  (byte) ((field[cur[0]][cur[1]][modTime] & TYPE_MASK) + BLACK);
    }
    return -1;
  }
  
  
}

