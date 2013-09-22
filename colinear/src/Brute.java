import java.util.Arrays;

public class Brute {
   private static final int STEP_SIZE = 4;

   private Point[] points;
   private Point[] combination;
   private Permutation permutator;

   public static void main(String[] args)
   {
      Point[] points = readPoints(args[0]);
      Arrays.sort(points);

      Brute brute = new Brute();
      brute.init(points, STEP_SIZE);
      if (brute.isValidInput())
      {
         brute.findCollinear();
      }
   }

   private void init(Point[] points, int stepSize)
   {
      this.points = points;
      this.combination = new Point[stepSize];
      this.permutator = new Permutation(stepSize, points.length - 1);
   }

   private void findCollinear()
   {
      while(nextStep())
      {
         if (isCollinear(combination)) print();
      }

   }

   private void print()
   {
      Point point = null;
      Arrays.sort(combination);
      StdDraw.setXscale(0, 32768);
      StdDraw.setYscale(0, 32768);
      StdDraw.show(0);
      StdDraw.setPenRadius(0.02);
      for (int i = 0; i < combination.length; i++)
      {
         Point p = combination[i];
         //print to standard output
         StdOut.print(p);
         if (i < combination.length - 1)
         {
            StdOut.print(" -> ");
         }

         //draw point
         p.draw();

         //draw line
         if (point != null)
         {
            point.drawTo(p);
         }

         point = p;
      }

      StdOut.println();
      StdDraw.show(0);
   }

   private boolean nextStep()
   {
      int[] perm = permutator.nextPermutation();

      if (perm == null)
      {
         return false;
      }

      for (int i = 0; i < perm.length; i++)
      {
         combination[i] = points[perm[i]];
      }

      return true;
   }

   private static Point[] readPoints(String file)
   {
      In in = new In(file);
      int N = in.readInt();
      Point[] points = new Point[N];
      for (int i = 0; i < N; i++) {
         int x = in.readInt();
         int y = in.readInt();
         points[i] = new Point(x, y);
      }

      return points;
   }

   private static boolean isCollinear(Point... points)
   {
      if (points.length < 2)
      {
         throw new RuntimeException("Insufficient number of points: " + points.length);
      }

      Point p0 = points[0];
      Point p1 = points[1];
      double slope = p0.slopeTo(p1);

      for (int i = 2; i < points.length; i++)
      {
         Point p = points[i];
         if (slope != p0.slopeTo(p))
         {
            return false;
         }
      }

      return true;
   }

   private boolean isValidInput()
   {
      return points.length >= STEP_SIZE;
   }

   private static class Permutation
   {
      private final int[] permutation;
      private final int right;
      private final int max;

      private Permutation(int k, int max)
      {
         this.right = k - 1;
         this.max = max;
         this.permutation = new int[k];
         for (int i = 0; i < k - 1; i++)
         {
            permutation[i] = i;
         }
         permutation[k - 1] = k - 2;
      }

      int[] nextPermutation()
      {
         int right = this.right;
         if (permutation[right] == max)
         {
            int left = right - 1;
            int maxAllowed = max - 1;
            while (left >= 0 && permutation[left] == maxAllowed)
            {
               left--;
               maxAllowed--;
            }

            if (left < 0) return null;

            permutation[left]++;

            left++;
            maxAllowed++;
            int value = permutation[left - 1] + 1;
            while (left <= this.right && value < maxAllowed)
            {
               permutation[left] = value;
               left++;
               maxAllowed++;
               value = permutation[left - 1] + 1;
            }

            return permutation;
         }
         else
         {
            permutation[right]++;
            return permutation;
         }
      }

//      boolean hasNext()
//      {
//         for (int position : permutation)
//         {
//            if (position < max) return true;
//         }
//
//         return false;
//      }
   }
}