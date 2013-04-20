import java.util.Scanner;



public class POJ1416 {

  public static void main(String[] args) {
    StringBuilder sb = new StringBuilder();
    Scanner s = new Scanner(System.in);
    while(s.hasNextInt()){
      int n = s.nextInt();
      int m = s.nextInt();
      if(n == 0 && m == 0){
        break;
      }
      ResultData result = shredding(n, m);
      if(result.duplicate){
        sb.append("rejected");
      } else if(result.sum < 0){
        sb.append("error");
      } else {
        sb.append(result.sum);
        for (int i = result.cards.length - 1; i >=0 ; i--) {
          if(result.cards[i] == -1){
            continue;
          }
          sb.append(" ");
          sb.append(result.cards[i]);
        }
      }
      sb.append('\n');
    }
    System.out.println(sb);
  }

  public static ResultData shredding(int target, int card) {
    ResultData rd = new ResultData();
    rd.duplicate = false;
    if(target >= card){
      rd.cards = new int[]{card};
      rd.sum = card;
      return rd;
    }
    rd.cards = new int[String.valueOf(card).length()];
    for (int i = 0; i < rd.cards.length; i++) {
      rd.cards[i] = -1;
    }
    rd.sum = -1;
    for (int i = 10; card / i != 0; i*=10) {
      int higher = card / i;
      int lower = card % i;
      if(lower > target){
        break;
      }
      if(i != 10 && (lower * 10 / i == 0)){
        continue;
      }
      ResultData cur = shredding(target - lower , higher);
      if(cur.sum == -1){
        continue;
      }
      if(rd.sum == (cur.sum + lower) || cur.duplicate){
        rd.duplicate = true;
      }
      if(rd.sum < (cur.sum + lower)){
        rd.sum = (cur.sum + lower);
        rd.duplicate = cur.duplicate;
        System.arraycopy(cur.cards, 0, rd.cards, 1, cur.cards.length);
        rd.cards[0] = lower;
      }
    }
    return rd;
  }
  
  static class ResultData{
    public int[] cards;
    public int sum;
    public boolean duplicate;
  }
  
}
