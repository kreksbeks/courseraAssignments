import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Iterator;

/**
 * User: piligrim
 * Date: 9/13/13
 * Time: 8:39 PM
 */
public class DequeueTest
{
   int[] ascendingInts = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
   private Deque<Integer> deque;

   @Before
   public void init()
   {
      deque = new Deque<Integer>();
   }

   @Test
   public void addFirstRemoveLastTest()
   {
      for (int i : ascendingInts)
      {
         deque.addFirst(i);
      }

      for (int ascendingInt : ascendingInts)
      {
         Assert.assertEquals(ascendingInt, (int) deque.removeLast());
      }
   }

   @Test
   public void addLastRemoveFirstTest()
   {
      for (int i : ascendingInts)
      {
         deque.addLast(i);
      }

      for (int ascendingInt : ascendingInts)
      {
         Assert.assertEquals(ascendingInt, (int) deque.removeFirst());
      }
   }

   @Test
   public void addLastRemoveLastTest()
   {
      for (int i : ascendingInts)
      {
         deque.addLast(i);
      }

      for (int i = ascendingInts.length - 1; i >= 0; i--)
      {
         int ascendingInt = ascendingInts[i];
         Assert.assertEquals(ascendingInt, (int) deque.removeLast());
      }
   }

   @Test
   public void addFirstRemoveFirstTest()
   {
      for (int i : ascendingInts)
      {
         deque.addFirst(i);
      }

      for (int i = ascendingInts.length - 1; i >= 0; i--)
      {
         int ascendingInt = ascendingInts[i];
         Assert.assertEquals(ascendingInt, (int) deque.removeFirst());
      }
   }

   @Test
   public void addFirstAddLastRemoveFirstRemoveLastTest1()
   {
      java.util.Deque<Integer> stdDequeue = new ArrayDeque<Integer>();

      for (int i = 0; i < ascendingInts.length; i++)
      {
         if (i < ascendingInts.length / 2)
         {
            deque.addFirst(ascendingInts[i]);
            stdDequeue.addFirst(ascendingInts[i]);
         }
         else
         {
            deque.addLast(ascendingInts[i]);
            stdDequeue.addLast(ascendingInts[i]);

         }
      }

      Iterator<Integer> iterator = deque.iterator();
      for (Integer i : stdDequeue)
      {
         Assert.assertEquals(i, iterator.next());
      }
   }

   @Test
   public void addFirstAddLastRemoveFirstRemoveLastTest2()
   {
      java.util.Deque<Integer> stdDequeue = new ArrayDeque<Integer>();

      for (int i = 0; i < ascendingInts.length; i++)
      {
         if (i < ascendingInts.length / 2)
         {
            deque.addFirst(ascendingInts[i]);
            stdDequeue.addFirst(ascendingInts[i]);
         }
         else
         {
            deque.addLast(ascendingInts[i]);
            stdDequeue.addLast(ascendingInts[i]);

         }
      }

      for (int i = 0; i < ascendingInts.length; i++)
      {
         if (i < ascendingInts.length / 2)
         {
            Assert.assertEquals(stdDequeue.removeFirst(), deque.removeFirst());
         }
         else
         {
            Assert.assertEquals(stdDequeue.removeLast(), deque.removeLast());
         }
      }
   }

   @Test
   public void iteratorAfterAddFirstTest()
   {
      for (int i : ascendingInts)
      {
         deque.addFirst(i);
      }

      Iterator<Integer> iterator = deque.iterator();
      for (int i = ascendingInts.length - 1; i >= 0; i--)
      {
         Assert.assertEquals(ascendingInts[i], (int) iterator.next());
      }
   }
}
