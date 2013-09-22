import org.junit.Assert;
import org.junit.Test;

/**
 * User: piligrim
 * Date: 9/22/13
 * Time: 1:10 PM
 */
public class SlopeOrderComparatorTest
{
   Point p = new Point(317, 11);
   Point q = new Point(213, 37);
   Point r = new Point(427, 464);

   @Test
   public void testReflexiveCompare() throws Exception
   {
      Point q = new Point(213, 37);

      Assert.assertEquals(0, p.SLOPE_ORDER.compare(q, q));
   }

   @Test
   public void testAsymmetricCompare() throws Exception
   {
      Assert.assertFalse(p.SLOPE_ORDER.compare(q, r) == p.SLOPE_ORDER.compare(r, q));
   }


   @Test
   public void testAsymmetricCompare2() throws Exception
   {
      Point q = new Point(213, 37);
      Point r = new Point(213, 37);
      Assert.assertTrue(p.SLOPE_ORDER.compare(q, r) == p.SLOPE_ORDER.compare(r, q));
   }

   @Test
   public void testEqualsCompare() throws Exception
   {
      Point p = new Point(3, 1);
      Point q = new Point(9, 6);
      Point r = new Point(9, 6);
      Assert.assertEquals(0, p.SLOPE_ORDER.compare(q, r));
   }
}
