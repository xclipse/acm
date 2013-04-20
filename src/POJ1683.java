import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

// Accepted 3304K 172MS
public class POJ1683 {


  public static void main(String[] args) throws IOException {
    StringBuilder sb = new StringBuilder();
    Scanner s = new Scanner(System.in);
    int count = s.nextInt();
    for (int i = 0; i < count; i++) {
      N = s.nextInt();
      M = s.nextInt();
      mapping= new char[N][M];
      result= new char[M][N];
      for (int n = 0; n < N; n++) {
        String line = s.next();
        for (int m = 0; m < line.length(); m++) {
          mapping[n][m] = line.charAt(m);
        }
      }
      ArrayList<int[][]> relList = new ArrayList<int[][]>();
      ArrayList<int[][]> conList = new ArrayList<int[][]>();
      while(true){
        int idx1 = s.nextInt();
        int idy1 = s.nextInt();
        String relchar = s.next();
        int idx2 = s.nextInt();
        int idy2 = s.nextInt();
        if(idx1 == 0) break;
        if("R".equals(relchar)){
          relList.add(new int[][]{{idx1,idy1},{idx2,idy2}});
        } else {
          conList.add(new int[][]{{idx1,idy1},{idx2,idy2}});
        }
      }
      relative = new int[relList.size()][][];
      relList.toArray(relative);
      conflict = new int[conList.size()][][];
      conList.toArray(conflict);
      puzzle(0,0);
      for (int m = 0; m < M; m++) {
        for (int n = 0; n < N; n++) {
          sb.append(result[m][n]);
        }
        sb.append('\n');
      }
      sb.append('\n');
    }
    System.out.println(sb);
    s.close();
//    N = 3;
//    M = 4;
//    mapping= new char[N][M];
//    result= new char[M][N];
//    relative = new int[4][2][2];
//    conflict = new int[2][2][2];
//  mapping = new char[][] {{'A','B','C','D'},{'E','F','G','H'},{'I','J','K','L'}};
//  relative=new int[][][]{{{1,1},{3,2}},{{2,2},{3,4}},{{1,3},{2,3}},{{3,1},{1,3}}};
//  conflict=new int[][][]{{{1,2},{2,2}},{{1,1},{2,4}}};
//    puzzle(0,0);
//    System.out.println(result);    
  }

  private static char[][] mapping;
  private static char[][] result;
  private static int[][][] relative;
  private static int[][][] conflict;
  private static int N, M;
  
  public static boolean puzzle(int col, int row){

    boolean flag = false;
    int i,j,k;
    outer:
      for (i = 0; i < M; i++) {
        char[] rel = new char[N];
        rel[col] = mapping[col][i];
        getRelative(rel, mapping[col][i]);

        for (j = 0; j < N; j++) {
          if(rel[j] == 0) continue;
          if(result[row][j] != 0){
            continue outer;
          }
          
          for (k = 0; k < M; k++) {
            if(result[k][j] == rel[j]){
              continue outer;
            }
          }
          char[] conflict = getConflict(rel[j]);
          if(conflict != null){
            for (k = 0; k < N; k++) {
              if(result[row][k] != 0){
                for (int l = 0; l < conflict.length; l++) {
                  if(result[row][k] == conflict[l]){
                    continue outer;
                  }
                }
              }
            }
          }
        }
        for (j = 0; j < N; j++) {
          if(rel[j] != 0){
            result[row][j] = rel[j];
          }
        }
        int newRow = row,newCol = col + 1;
        for ( ; newRow < M; newRow++) {
          for ( ; newCol < N ; newCol++) {
            if(result[newRow][newCol] == 0){
              flag = puzzle(newCol, newRow);
              if(flag) return true;
              for (j = 0; j < N; j++) {
                if(rel[j] != 0){
                  result[row][j] = 0;
                }
              }
              continue outer;
            }
          }
          newCol = 0;
        }
        return true;
      }
    
    return false;
  }

  private static char[] getConflict(char c) {
    char[] con = new char[conflict.length];
    int idx = 0;
    for (int i = 0; i < conflict.length; i++) {
      for (int j = 0; j < conflict[i].length; j++) {
        if(mapping[conflict[i][j][0] - 1][conflict[i][j][1] -1 ] == c){
          con[idx++] = mapping[conflict[i][(j+1)%2][0] - 1][conflict[i][(j+1)%2][1] - 1];
          break;
        }
      }
    }
    if(idx == 0){
      return null;
    } 
    return Arrays.copyOf(con, idx);
  }

  private static char[] getRelative(char[] rel, char c) {

    for (int i = 0; i < relative.length; i++) {
      for (int j = 0; j < relative[i].length; j++) {
        if(mapping[relative[i][j][0] - 1][relative[i][j][1] - 1] == c && rel[relative[i][(j+1)%2][0] - 1] == 0){
          rel[relative[i][(j+1)%2][0] - 1] = mapping[relative[i][(j+1)%2][0] - 1][relative[i][(j+1)%2][1] - 1];
          getRelative(rel, rel[relative[i][(j+1)%2][0] - 1] );
          break;
        }
      }
    }

    return rel;
  }

}
