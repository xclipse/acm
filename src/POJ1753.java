import java.io.IOException;
import java.util.Scanner;

// Accepted 2996K 2829MS
public class POJ1753 {

// POJ1753
  public static void main(String[] args) throws IOException {
    Scanner s = new Scanner(System.in);
    for (int r = 0; r < DIM; r++) {
      char[] line = s.next().toCharArray();
      for (int c = 0; c < DIM; c++) {
        if('b' == line[c]){
          field[r+1][c+1] = true;
        } else {
          field[r+1][c+1] = false;
        }
      }
    }
    s.close();
    FlipEnum();
    if(minFlipCount == (DIM * DIM + 1)){
      System.out.println("Impossible");
    } else {
      System.out.println(minFlipCount);
    }
  }

  private final static int  DIM = 4;
  // false == white / true == black
  private static boolean[][] field = new boolean[DIM + 2][DIM + 2];
  private static boolean[][] whiteFilp = new boolean[DIM + 2][DIM + 2];
  private static boolean[][] blackFilp = new boolean[DIM + 2][DIM + 2];
  private static int whileFlipCount, blackFlipCount, minFlipCount;
  
  public static void FlipEnum(){
    minFlipCount = DIM * DIM + 1;
    int allMask = (int)Math.pow(2, DIM);
    outer:
    for (int i = 0; i < allMask; i++) {
      int r,c;
      whileFlipCount = blackFlipCount = 0;
      for (c = 1; c <= DIM; c++) {
        whiteFilp[1][c] = blackFilp[1][c] = (i>>(c-1)&1) == 1;
        if(whiteFilp[1][c]){
          whileFlipCount++;
          blackFlipCount++;
        }
      }
      for (r = 2; r <= DIM; r++) {
        if(whileFlipCount > minFlipCount && blackFlipCount > minFlipCount){
          continue outer;
        }
        for(c = 1; c <= DIM; c++){
          whiteFilp[r][c] = field[r - 1][c] ^ whiteFilp[r - 1][c] ^ whiteFilp[r - 1][c + 1] ^ whiteFilp[r - 1][c - 1] ^ whiteFilp[r - 2][c];
          blackFilp[r][c] = !(field[r - 1][c] ^ blackFilp[r - 1][c] ^ blackFilp[r - 1][c + 1] ^ blackFilp[r - 1][c - 1] ^ blackFilp[r - 2][c]);
          if(whiteFilp[r][c]) whileFlipCount ++;
          if(blackFilp[r][c]) blackFlipCount ++;
        }
      }
      boolean wTest = true, bTest = true;
      for (c = 1; c <= DIM; c++) {
        if(wTest && field[DIM][c] ^ whiteFilp[DIM][c] ^ whiteFilp[DIM][c + 1] ^ whiteFilp[DIM][c - 1] ^ whiteFilp[DIM - 1][c]){
          wTest = false;
        }
        if(bTest && !(field[DIM][c] ^ blackFilp[DIM][c] ^ blackFilp[DIM][c + 1] ^ blackFilp[DIM][c - 1] ^ blackFilp[DIM - 1][c])){
          bTest = false;
        }
      }
      if(wTest && whileFlipCount < minFlipCount){
        minFlipCount = whileFlipCount;
      }
      if(bTest && blackFlipCount < minFlipCount){
        minFlipCount = blackFlipCount;
      }
    }
  }
}
