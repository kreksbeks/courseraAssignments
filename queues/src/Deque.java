import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
   private Node<Item> first;
   private Node<Item> last;
   private int size;

   // construct an empty deque
   public Deque()
   {
   }

   // is the deque empty?
   public boolean isEmpty()
   {
      return size == 0;
   }

   // return the number of items on the deque
   public int size()
   {
      return size;
   }

   // insert the item at the front
   public void addFirst(Item item)
   {
      validate(item);

      Node<Item> oldFirst = first;

      first = new Node<Item>();
      first.i = item;

      first.next = oldFirst;

      if (oldFirst == null)
      {
         last = first;
      }
      else
      {
         oldFirst.prev = first;
      }

      size++;
   }

   // insert the item at the end
   public void addLast(Item item)
   {
      validate(item);

      Node<Item> oldLast = last;
      last = new Node<Item>();
      last.i = item;

      last.prev = oldLast;

      if (oldLast == null)
      {
         first = last;
      }
      else
      {
         oldLast.next = last;
      }

      size++;
   }

   // delete and return the item at the front
   public Item removeFirst()
   {
      checkNotEmpty();

      Node<Item> oldFirst = first;
      first = first.next;

      if (first == null)
      {
         last = null;
      }
      else
      {
         first.prev = null;
      }

      oldFirst.next = null;

      size--;

      return oldFirst.i;
   }

   // delete and return the item at the end
   public Item removeLast()
   {
      checkNotEmpty();

      Node<Item> oldLast = last;
      last = last.prev;

      if (last == null)
      {
         first = null;
      }
      else {
         last.next = null;
      }

      oldLast.prev = null;

      size--;

      return oldLast.i;
   }

   // return an iterator over items in order from front to end
   public Iterator<Item> iterator()
   {
      return new DequeIterator();
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

   private class DequeIterator implements Iterator<Item>
   {
      Node<Item> next;

      private DequeIterator()
      {
         this.next = first;
      }

      @Override
      public boolean hasNext()
      {
         return next != null;
      }

      @Override
      public Item next()
      {
         if (next == null)
         {
            throw new NoSuchElementException();
         }

         Item i = next.i;
         next = next.next;
         return i;
      }

      @Override
      public void remove()
      {
         throw new UnsupportedOperationException();
      }
   }

   private static class Node<Item>
   {
      Node<Item> next;
      Node<Item> prev;
      Item i;
   }
}