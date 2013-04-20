import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadLine {

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader( new InputStreamReader(System.in));
    String line;
    while((line = br.readLine()) != null){
      if(line.trim().length() == 0 ) break;
      System.out.println(line);
    }
  }

}
