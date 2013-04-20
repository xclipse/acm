
public class Light {

  public static void main(String[] args) {

    for (int i = 0; i < 64; i++) {
      for (int j = 0; j < 6; j++) {
        System.out.print(i >> j & 1);
      }
      System.out.println();
    }
  }

}
