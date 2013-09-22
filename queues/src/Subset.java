public class Subset {
   public static void main(String[] args)
   {
      int k = Integer.valueOf(args[0]);

      RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();

      while (!StdIn.isEmpty())
      {
         randomizedQueue.enqueue(StdIn.readString());
      }

      for ( ; k > 0 ; k--)
      {
         StdOut.println(randomizedQueue.dequeue());
      }
   }
}