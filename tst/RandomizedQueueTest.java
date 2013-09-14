import org.junit.Before;
import org.junit.Test;

/**
 * User: piligrim
 * Date: 9/13/13
 * Time: 10:24 PM
 */
public class RandomizedQueueTest
{
   private static final int SIZE = 150;
   int[] ascendingInts;
   private RandomizedQueue<Integer> queue;

   @Before
   public void init()
   {
      ascendingInts = new int[SIZE];
      for (int i = 0; i < SIZE; i++)
      {
         ascendingInts[i] = i;
      }

      queue = new RandomizedQueue<Integer>();
   }

   @Test
   public void test()
   {
      for (int i : ascendingInts)
      {
         queue.enqueue(i);
      }

      for (int i = 0; i < SIZE; i++)
      {
         System.out.println(queue.dequeue());
      }
   }

   @Test
   public void test2()
   {
      for (int i : ascendingInts)
      {
         queue.enqueue(i);
      }

      for (Integer i : queue)
      {
         System.out.println(i);
      }
   }

   @Test
   public void test3()
   {
      for (int i : ascendingInts)
      {
         queue.enqueue(i);
      }

      for (int i = 0; i < SIZE; i++)
      {
         System.out.println(queue.sample());
      }
   }
}
