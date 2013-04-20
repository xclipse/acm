import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;


public class POJ1924_MEM {

  // 1924 Memory Limit Exceeded

  public static boolean boun(int x, int boundary){
    return x >=0 && x <boundary;
  }
  public static void main(String[] args) {

    Scanner s = new Scanner(System.in);
    StringBuilder sb = new StringBuilder();
    while (true) {
      
      int r,c;
      r = s.nextInt();
      c = s.nextInt();
      if(r == 0){
        break;
      }
      int px = 0,py = 0,tx,ty;
      field = new BlockSet[r][c];
//      field = new BlockSet[r + 4][c + 4];
//      BlockSet rockBlockSet = new BlockSet();
//      rockBlockSet.add(new Block(0, 0, 0), 0);
//      rockBlockSet.getBlock(0).setType(Block.TYPE.ROCK);
//      for (int i = 0; i < r + 4; i++) {
//        field[i][0] = field[i][1] = field[i][c+2] =field[i][c+3] = rockBlockSet;
//      }
//      for (int j = 0; j < c + 4; j++) {
//        field[0][j] = field[1][j] = field[r+2][j] = field[r+3][j] = rockBlockSet;
//      }
      ArrayList<Block.TYPE> monType = new ArrayList<Block.TYPE>();
      for (byte y = 0; y < r; y++) {
        char[] row = s.next().toCharArray();
        for (byte x = 0; x < row.length; x++) {
          Block b = new Block(y, x, 0);
          switch (row[x]) {
          case '#':
            b.setType(Block.TYPE.ROCK);
            break;
          case 't':
            ty=y;
            tx=x;
            b.setType(Block.TYPE.TARGET);
            break;
          case 'p':
            py = y;
            px = x;
            b.setType(Block.TYPE.PLAYER);
            break;
          case 'a':
            monType.add(Block.TYPE.AGG_MONSTER);
            break;
          case 'n':
            monType.add(Block.TYPE.NON_AGG_MONSTER);
            break;
          }
          field[y][x] = new BlockSet();
          field[y][x].add(b, 0);
        }
      }
      int monNum = s.nextInt();
      for (int i = 0; i < monNum; i++) {
        int step = s.nextInt();
        boolean isAgg = monType.get(i) == Block.TYPE.AGG_MONSTER;

        for (int time = 0; time < step; time++) {
          int monY = s.nextInt() - 1;
          int monX = s.nextInt() - 1;
          
          Block b = new Block(monY, monX, time);
          b.setType(isAgg?Block.TYPE.AGG_MONSTER:Block.TYPE.NON_AGG_MONSTER);
          field[monY][monX].add(b, time);
          if(isAgg){
            for (int tranIdx = 0; tranIdx < tranNum.length; tranIdx++) {
              b = new Block(monY + tranNum[tranIdx][0], monX + tranNum[tranIdx][1], time);
              b.setType(Block.TYPE.AGG_MONSTER);
              field[b.y][b.x].add(b, time);
            }
          }
          
        }
      }
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

  public static BlockSet [][] field;

  public static byte[][] tranNum = new byte[][]{
    {1,0},{1,1},{0,1},{-1,1},{-1,0},{-1,-1},{0,-1},{1,-1}};
  public static int search(int y, int x){
    int time = 0;
    LinkedList<Block> q = new LinkedList<Block>();
    q.add(new Block(y, x, time));
    field[y][x].getBlock(0).setType(Block.TYPE.PLAYER);
    while (!q.isEmpty() && time <= 100){
      time++;
      int size = q.size();
      for (int i = 0; i < size; i++) {
        Block cur = q.removeFirst();
        for (int tranIdx = 0; tranIdx < tranNum.length; tranIdx++) {
          // try to move 2 step in one direction
          for (int repeatMove = 0; repeatMove < 2; repeatMove++) {
            int my = cur.y + tranNum[tranIdx][0] * (repeatMove + 1);
            int mx = cur.x + tranNum[tranIdx][1] * (repeatMove + 1);
            if(!boun(my, field.length) || !boun(mx, field[0].length)){
              continue;
            }
            Block tarBlock = field[my][mx].getBlock(time);
            if(tarBlock.isTarget()){
              Block cb = cur;
              while (cb != null){
                cb = cb.pre;
              }
              return time;
            }
            if(tarBlock.isAvaliable()){
              tarBlock.setStatus(Block.STATUS.GRAY);
              tarBlock.pre=cur;
              q.add(tarBlock);
            } else {
              // failed to move any more step in this direction
              break;
            }
          }
        }
        // check is it necessary to stay on the same block for one more round
        for (int tranIdx = 0; tranIdx < tranNum.length; tranIdx++) {
          int my = cur.y + tranNum[tranIdx][0];
          int mx = cur.x + tranNum[tranIdx][1];
          if(!boun(my, field.length) || !boun(mx, field[0].length)){
            continue;
          }
          if(field[my][mx].isAvaliable()){
            Block e = new Block(cur.y, cur.x, time);
            e.setStatus(Block.STATUS.GRAY);
            e.pre=cur;
            q.add(e);
            break;
          }
        }
        cur.setStatus(Block.STATUS.BLACK);
      }
    }
    return -1;
  }
  
  static class Block{
    public Block pre;

    public enum TYPE{
      ROCK,AGG_MONSTER, NON_AGG_MONSTER,PLAYER,TARGET,FREE;
    }
    public enum STATUS{
      WHITE,GRAY,BLACK;
    }
    public Block(int y, int x, int time) {
      super();
      this.x = x;
      this.y = y;
      this.time = time;
      setType(TYPE.FREE);
      setStatus(STATUS.WHITE);
    }

    public void setTime(int time) {
      this.time = time;
    }

    public boolean isTarget() {
      if(type == TYPE.TARGET)
        return true;
      return false;
    }

    int x,y,time;
    private STATUS status; 
    private TYPE type;
    public void setStatus(STATUS status) {
      this.status = status;
    }
    public void setType(TYPE type) {
      if(type == TYPE.ROCK || type == TYPE.AGG_MONSTER || type == TYPE.NON_AGG_MONSTER){
        status = STATUS.BLACK;
      }
      this.type = type;
    }

    public boolean isAvaliable(){
      if(status == STATUS.WHITE){
        return true;
      }
      return false;
    }
    
    @Override
    public String toString() {
      return "["+(y - 1)+","+ (x - 1) +"," +time+"]";
    }
  }
  
  static class BlockSet{
    private ArrayList<Block> list = new ArrayList<Block>(1);
    public BlockSet add(Block b, int time){
      if(time != 0 && (!boun(b.y, field.length) || !boun(b.x, field[0].length) || this.getBlock(0).type != Block.TYPE.ROCK)){
        return null;
      }
      if(list.size() > time){
        if(!b.isAvaliable() && list.get(time).isAvaliable()){
          list.set(time, b);
        }
      } else {
        while(list.size() < time){
          list.add(new Block(b.x, b.y, list.size() - 1));
        }
        list.add(b);
      }
      return this;
    }
    
    public Block getBlock(int time){
      return list.get(time % list.size());
    }
    
    public boolean isAvaliable(){
      for (Block b : list) {
        if(b.isAvaliable()){
          return true;
        }
      }
      return false;
    }
  }
}

