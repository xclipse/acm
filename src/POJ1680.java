import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

//Accepted  5040K 188MS
public class POJ1680 {


  public static void main(String[] args) throws IOException {
    StringBuilder sb = new StringBuilder();
    Scanner s = new Scanner(System.in);
    int count = s.nextInt();
    for (int i = 0; i < count; i++) {
      sb.append(getLineByStack(s.nextInt(), s.nextInt()));
    }
    System.out.println(sb);
    s.close();
//    n = 4;
//    line = 19;
//    System.out.println(getLine(0, 0));
//    System.out.println(getLineByStack(4, 4));
  }
  private static int line;
  private static int n;
  private static int pid = 1000;
  public static String getLine(int start, int A){
    int curpid = pid ++;
    for (int i = start; i < n; i++) {
      line--;
      if(line == 0){
        return "Loop " + i + ": Process ID=" + curpid + "\n";
      }
      line --;
      A+=7;
      if(line == 0){
        return "Process ID=" + pid + ", A=" + A + "\n";
      }
      
      String rtn =  getLine(i + 1, A);
      if(!rtn.equals("")){
        return rtn;
      }
    }
    return "";
  }
  
  public static String getLineByStack(int n, int line){
    Stack<int[]> stack = new Stack<int[]>();
    int pid = 1000;
    int count = 0;
    int[] curProcess = new int[]{1000,0,0};
    while(curProcess != null){
      if(curProcess[1] < n){
        count ++;
        if(count == line){
          return "Loop " + curProcess[1] + ": Process ID=" + curProcess[0] + "\n";
        }
        curProcess[1] ++;
        curProcess[2] += 7;
        int[] newProcess = new int[]{++pid, curProcess[1], curProcess[2]};
        count ++;
        if(count == line){
          return "Process ID=" + newProcess[0] + ", A=" + newProcess[2] + "\n";
        }
        stack.push(curProcess);
        curProcess = newProcess;
      } else if(!stack.isEmpty()){
        curProcess = stack.pop();
      } else {
        curProcess = null;
      }

    }
    return null;
  }

}
