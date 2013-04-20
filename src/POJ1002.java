import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TreeMap;

public class POJ1002 {

  private static char[][] mapping = { { '1', '1' }, { 'A', '2' }, { 'B', '2' }, { 'C', '2' }, { '2', '2' }, { 'D', '3' },
      { 'E', '3' }, { 'F', '3' }, { '3', '3' }, { 'G', '4' }, { 'H', '4' }, { 'I', '4' }, { '4', '4' }, { 'J', '5' },
      { 'K', '5' }, { 'L', '5' }, { '5', '5' }, { 'M', '6' }, { 'N', '6' }, { 'O', '6' }, { '6', '6' }, { 'P', '7' },
      { 'R', '7' }, { 'S', '7' }, { '7', '7' }, { 'T', '8' }, { 'U', '8' }, { 'V', '8' }, { '8', '8' }, { 'W', '9' },
      { 'X', '9' }, { 'Y', '9' }, { '9', '9' }, { '0', '0' }, };

  public static void main(String[] args) throws IOException {
    TreeMap<String, Integer> t = new TreeMap<String, Integer>();
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringBuilder sb = new StringBuilder();
    String line;
    while ((line = br.readLine()) != null) {
      if (line.trim().length() == 0)
        break;
      char[] ca = line.replaceAll("-", "").toCharArray();
      if (ca.length != 7)
        continue;
      sb.delete(0, sb.length());
      for (int i = 0; i < ca.length; i++) {
        for (int j = 0; j < mapping.length; j++) {
          if (mapping[j][0] == ca[i]) {
            sb.append(mapping[j][1]);
            if (sb.length() == 3) {
              sb.append('-');
            }
            break;
          }
        }
      }
      if (sb.length() == 8) {
        if(t.containsKey(sb.toString())){
          t.put(sb.toString(), t.get(sb.toString()).intValue() + 1);
        } else {
          t.put(sb.toString(), 1);
        }
      }
    }
    boolean notdup = true;
    for (String key : t.keySet()) {
      if(t.get(key) > 1){
        System.out.println(key + " " + t.get(key));
        notdup = false;
      }
    }
    if(notdup){
      System.out.println("No duplicates.");
    }
  }
}
