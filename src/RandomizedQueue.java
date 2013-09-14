import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
   private Item[] items;
   private int size = 0;

   // construct an empty randomized queue
   @SuppressWarnings("unchecked")
   public RandomizedQueue()
   {
      items = (Item[]) new Object[5];
   }

   // is the queue empty?
   public boolean isEmpty()
   {
      return size <= 0;
   }

   // return the number of items on the queue
   public int size()
   {
      return size;
   }

   // add the item
   public void enqueue(Item item)
   {
      validate(item);

      if (items.length == size)
      {
         resize(items.length * 2);
      }

      items[size++] = item;
   }

   // delete and return a random item
   public Item dequeue()
   {
      checkNotEmpty();

      if (items.length / 4 > size)
      {
         resize(items.length / 2);
      }

      int last = size - 1;
      swap(items, nextIndex(), last);
      Item toReturn = items[last];
      items[last] = null;
      size--;

      return toReturn;
   }

   // return (but do not delete) a random item
   public Item sample()
   {
      checkNotEmpty();

      return items[nextIndex()];
   }

   // return an independent iterator over items in random order
   public Iterator<Item> iterator()
   {
      return new RandomizedQueueIterator();
   }

   @SuppressWarnings("unchecked")
   private void resize(int newSize)
   {
      Item[] newArr = (Item[]) new Object[newSize];
      System.arraycopy(items, 0, newArr, 0, size);
      items = newArr;
   }

   private int nextIndex()
   {
      return StdRandom.uniform(0, size);
   }

   private void checkNotEmpty()
   {
      if (isEmpty())
      {
         throw new NoSuchElementException();
      }
   }

   private void validate(Item item)
   {
      if (item == null)
      {
         throw new NullPointerException();
      }
   }

   private class RandomizedQueueIterator implements Iterator<Item>
   {
      int next;
      int[] indices;

      private RandomizedQueueIterator()
      {
         this.indices = new int[size()];
         for (int i = 0; i < size(); i++)
         {
            indices[i] = i;
         }
      }

      @Override
      public boolean hasNext()
      {
         return next < size();
      }

      @Override
      public Item next()
      {
         if (!hasNext())
         {
            throw new NoSuchElementException();
         }

         int nextIndex = StdRandom.uniform(next, indices.length);
         Item item = items[indices[nextIndex]];

         swap(indices, next++, nextIndex);

         return item;
      }

      @Override
      public void remove()
      {
         throw new UnsupportedOperationException();
      }
   }

   private static void swap(int[] arr, int j, int i)
   {
      int t = arr[j];
      arr[j] = arr[i];
      arr[i] = t;
   }

   private static <T> void swap(T[] arr, int j, int i)
   {
      T t = arr[j];
      arr[j] = arr[i];
      arr[i] = t;
   }

}