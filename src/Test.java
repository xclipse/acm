import java.text.ParseException;
import java.util.ArrayList;
import java.util.Random;


public class Test {

  /**
   * @param args
   * @throws ParseException 
   */
  public static void main(String[] args) throws ParseException {


    StringBuilder sb = new StringBuilder();
    Random r = new Random();
    int row = r.nextInt(100);
    int col = r.nextInt(100);
    byte[][] map = new byte[row][col];
    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map[i].length; j++) {
        map[i][j]=(byte) ((r.nextBoolean() && r.nextBoolean())? 1:0);
      }
    }
    int py = r.nextInt(row);
    int px = r.nextInt(col);
    int ty = r.nextInt(row);
    int tx = r.nextInt(col);
    map[py][px] = 2;
    map[ty][tx] = 3;
    
    ArrayList<byte[][]> mon = new ArrayList<byte[][]>();
    while(r.nextInt(9) >= 1){
      int n = r.nextInt(10);
      byte[][] bs = new byte[n + 1][];
      byte[] b = new byte[]{(byte) r.nextInt(row),(byte) r.nextInt(col)};
      bs[0] = b;
      mon.add(bs);
      map[b[0]][b[1]]=(byte) (r.nextBoolean()?4:5);
      for (int i = 0; i < n; i++) {
        bs[i + 1] = new byte[]{(byte) r.nextInt(row),(byte) r.nextInt(col)};
      }
    }
    
    sb.append(row).append(' ').append(col).append('\n');
    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map[i].length; j++) {
        switch(map[i][j]){
        case 0:
          sb.append('.');
          break;
        case 1:
          sb.append('#');
          break;
        case 2:
          sb.append('p');
          break;
        case 3:
          sb.append('t');
          break;
        case 4:
          sb.append('a');
          break;
        case 5:
          sb.append('n');
          break;
        }
      }
      sb.append('\n');
    }
    sb.append(mon.size() + "\n");
    for (byte[][] bs : mon) {
      sb.append(bs.length).append(' ');
      for (byte[] b : bs) {
        sb.append(b[0]).append(' ');
        sb.append(b[1]).append(' ');
      }
      sb.append('\n');
    }
  
    System.out.println(sb);
  }

}
