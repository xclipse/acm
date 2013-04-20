import java.util.Scanner;


public class POJ1856 {

  // 1856 Accepted  6600K 2235MS
  public static void main(String[] args) {
    
    Scanner s = new Scanner(System.in);
    StringBuilder sb = new StringBuilder();
    while(true){
      int r = s.nextInt();
      int c = s.nextInt();
      if(r==0) break;
      for (int i = 0; i <= r+1; i++) {
        for (int j = 0; j <= c+1; j++) {
          field[i][j] = false;
        }
      }
      for (int i = 1; i <= r; i++) {
        char[] row = s.next().toCharArray();
        for (int j = 1; j <= c; j++) {
          if(row[j - 1] == '.') {
            field[i][j] = false;
          } else {
            field[i][j] = true;
          }
        }
      }
      int shipNum = shipCount(r, c);
      if(shipNum < 0){
        sb.append("Bad placement.\n");
      } else {
        sb.append("There are " ).append(shipNum).append(" ships.\n");
      }
    }
    System.out.println(sb);
    s.close();
  }
  
  public static boolean[][] field = new boolean[1002][1002];

  public static int shipCount(int r, int c){
    int shipNum = 0;
    for (int row = 1; row <= r; row++) {
      for (int col = 1; col <= c; col++) {
        if(field[row][col]){
          int boty = row;
          int botx = col;
          for(;botx <=c; botx++){
            if(!field[row][botx + 1]){
              break;
            }
          }
          for(;boty <=r; boty++){
            if(!field[boty + 1][col]){
              break;
            }
          }
          boolean result = fillShip(row, col, boty, botx);
          if(!result){
            return -1;
          }
          shipNum ++;
          col = botx + 1;
        }
      }
    }
    return shipNum;
  }
  public static boolean fillShip(int topy, int topx, int boty, int botx){
    for (int i = topx - 1; i <= botx + 1; i++) {
      if(field[topy-1][i] || field[boty+1][i]){
        return false;
      }
    }
    for (int i = topy - 1; i <= boty + 1; i++) {
      if(field[i][topx - 1] || field[i][botx + 1]){
        return false;
      }
    }
    for (int i = topy; i <= boty; i++) {
      for (int j = topx; j <= botx; j++) {
        if(!field[i][j]){
          return false;
        }
        field[i][j] = false;
      }
      
    }
    return true;
  }
}
