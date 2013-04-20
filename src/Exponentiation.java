import java.math.BigDecimal;
import java.util.Scanner;



public class Exponentiation {

  public static void main(String[] args) {
    
    Scanner s = new Scanner(System.in);
    while(s.hasNextFloat() || s.hasNextInt()){
      float r = s.nextFloat();
      int n = s.nextInt();
      System.out.println(r + "   " + n + " = "+ new BigDecimal(r).pow(n));
    }
  }

}
