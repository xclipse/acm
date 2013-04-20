import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;


public class POJ1691 {

  // 1691 Accepted  3736K 157MS
  public static void main(String[] args) {

    Scanner s = new Scanner(System.in);
    StringBuilder sb = new StringBuilder();
    int n = s.nextInt();
    for (int i = 0; i < n; i++) {
      int bNum = s.nextInt();
      readyBlock = new ArrayList<Block>(bNum);
      Arrays.fill(colorBit, false);
      for (int j = 0; j < bNum; j++) {
        Block b = new Block();
        b.topY = s.nextInt();
        b.topX = s.nextInt();
        b.bottomY = s.nextInt();
        b.bottomX = s.nextInt();
        b.color = s.nextInt();
        colorBit[b.color] = true;
        readyBlock.add(b);
      }
      
      for (int j = 0; j < readyBlock.size(); j++) {
        for (int k = 0; k < readyBlock.size(); k++) {
          if(j == k){
            continue;
          }
          Block src = readyBlock.get(j);
          Block tar = readyBlock.get(k);
          if(src.bottomY == tar.topY && src.topX < tar.bottomX && src.bottomX > tar.topX){
            src.below.add(tar);
            tar.above.add(src);
          }
        }
      }
      for (Iterator<Block> itr = readyBlock.iterator(); itr.hasNext();) {
        Block b = itr.next();
        if(!b.above.isEmpty()){
          itr.remove();
        }
      }
      
      minChangedCount = bNum;
      for (int j = 1; j < 20; j++) {
        if(!colorBit[j]){
          continue;
        }
        curColor = j;
        changedCount = 1;
        paint(j);
      }
      sb.append(minChangedCount).append('\n');
    }
    System.out.println(sb);
  }

  
  static class Block{
    public int topX,topY,bottomX,bottomY;
    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + bottomX;
      result = prime * result + bottomY;
      result = prime * result + topX;
      result = prime * result + topY;
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
      Block other = (Block) obj;
      if (bottomX != other.bottomX)
        return false;
      if (bottomY != other.bottomY)
        return false;
      if (topX != other.topX)
        return false;
      if (topY != other.topY)
        return false;
      return true;
    }
    List<Block> below = new ArrayList<Block>();
    List<Block> above = new ArrayList<Block>();
    public int color;
    boolean colored = false;
    public boolean isReady(){
      for (Block a : above) {
        if(!a.colored) return false;
      }
      return true;
    }
    
    @Override
    public String toString() {
      return "< [" + color + "] [" +topY + ", " + topX + "]" + " [" + bottomY + ", " + bottomX + "] >";
    }
  }
  
  public static int aboveCount;
  
  public static boolean[] colorBit = new boolean[21];
  public static int curColor,changedCount, minChangedCount;
  public static List<Block> readyBlock;

  public static void paint(int color){
    List<Block> coloredBlock = new ArrayList<Block>();
    boolean needColorChange = color != curColor;
    int saveCurColor = curColor;
    for (Iterator<Block> itr = readyBlock.iterator(); itr.hasNext();) {
      Block block = itr.next();
      if(block.color == color){
        if(needColorChange && minChangedCount <= changedCount){
          return;
        }
        coloredBlock.add(block);
        itr.remove();
        block.colored = true;
      }
    }
    if(!coloredBlock.isEmpty()){
      if(color != curColor) {
        changedCount++;
        curColor = color;
      }

      List<Block> newReady = new ArrayList<Block>();
      boolean colorRemain = false;
      for (Block block : coloredBlock) {
        block.colored = true;
        if(block.below != null){
          for (Block below : block.below) {
            if(below.isReady()){
              newReady.add(below);
              if(below.color == color){
                colorRemain = true;
              }
            }
          }
        }
      }
      if(!newReady.isEmpty()){
        readyBlock.addAll(newReady);
      }
      if(readyBlock.isEmpty()){
        if(minChangedCount > changedCount){
          minChangedCount = changedCount;
        }
      } else {
        if(colorRemain){
          paint(color);
        } else {
          for (int i = 1; i <= 20; i++) {
            if(!colorBit[i] || i == color){
              continue;
            }
            paint(i);
          }
        }
      }
      readyBlock.removeAll(newReady); 
      for (Block block : coloredBlock) {
        block.colored = false;
      }
      curColor = saveCurColor;
      if(needColorChange){
        changedCount --;
      }
      readyBlock.addAll(coloredBlock);
    }
  }
}
