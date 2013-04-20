

import static org.junit.Assert.*;

import org.junit.Test;

public class UnitTest {

  //@Test
  public void testPOJ1579() {
    System.out.println(POJ1579.w(50, 50, 50));
  }
  //@Test
  public void testPOJ1416() {
    System.out.println(POJ1416.shredding(50, 12346));
    System.out.println(POJ1416.shredding(376, 144139));
    System.out.println(POJ1416.shredding(927438, 927438));
    System.out.println(POJ1416.shredding(18, 3312));
    System.out.println(POJ1416.shredding(9, 3142));
    System.out.println(POJ1416.shredding(6, 1104));
  }
  
  public void testPOJ1771(){
    POJ1771.main(null);
  }

  @Test
  public void testMain(){}
}
