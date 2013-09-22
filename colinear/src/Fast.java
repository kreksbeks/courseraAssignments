import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Fast {

   private static final int COLLINEAR_SET = 3;

   public static void main(String[] args)
   {
      alternative(args[0]);
   }

   private static void alternative(String arg)
   {
      Point[] points = readPoints(arg);
      Arrays.sort(points);

      for (int i = 0; i < points.length - 3; i++)
      {
         List<Point> collinear;
         int from = i + 1;
         Point p0 = points[i];
         Arrays.sort(points, from, points.length, p0.SLOPE_ORDER);
         int left = 1;
         for ( ; left < points.length; )
         {
            collinear = getCollinearPoints(p0, points, left);

            if (collinear.size() > 3)
            {
               draw(collinear);
            }
            left += collinear.size() - 1;
         }
      }
   }

//   private static List<Point> readPointsToList(String arg)
//   {
//      String filename = arg;
//      In in = new In(filename);
//      int N = in.readInt();
//      List<Point> points = new ArrayList<Point>(N);
//      for (int i = 0; i < N; i++) {
//         int x = in.readInt();
//         int y = in.readInt();
//         points.add(new Point(x, y));
//      }
//
//      return points;
//   }

//   private static void alternative2(String arg)
//   {
//      Point[] points = readPoints(arg);
//      Arrays.sort(points);
//
//      for (int i = 1; i < points.length - 3; i++)
//      {
//         List<Point> colliear;
//         int offset;
//         int from = i - 1;
//         Point p0 = points[from];
//         Arrays.sort(points, i, points.length, p0.SLOPE_ORDER);
//         for (int j = i; j < points.length; j++)
//         {
//            if ((colliear = getCollinearPoints(p0, points, 1)) != null )
//            {
//               draw(colliear);
////               i += offset;
//            }
//         }
//      }
//   }

   private static List<Point> getCollinearPoints(Point p0, Point[] points, int left)
   {
      List<Point> collinear = new ArrayList<Point>();

      double slope0 = p0.slopeTo(points[left]);
      collinear.add(p0);
      collinear.add(points[left]);

      left++;
      for ( ; left < points.length; left++)
      {
         double slope = p0.slopeTo(points[left]);
         if (slope0 != slope)
         {
            break;
         }

         collinear.add(points[left]);
      }

      return collinear;
   }

   private static int getCollinearPointsOffset(Point p0, Point[] points, int from)
   {
      double slope0 = p0.slopeTo(points[from]);

      int offset = 1;
      for (int i = from + 1; i < points.length; i++)
      {
         double slope = p0.slopeTo(points[i]);
         if (slope0 != slope)
         {
            break;
         }

         offset++;
      }

      return offset;
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

   private static void print(Point[] points, int from, int offset)
   {
      Point point = null;
      for (int i = from; i < points.length && offset >= 0; i++)
      {
         offset--;

         Point p = points[i];
         //print to standard output
         StdOut.print(p);
         if (offset >= 0)
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
   }

   private static void draw(List<Point> points)
   {
      Point p1 = points.get(0);
      Point p2 = points.get(1);
      if (p1.compareTo(p2) >= 0) return;

      StdDraw.setXscale(0, 32768);
      StdDraw.setYscale(0, 32768);
      StdDraw.show(0);
      StdDraw.setPenRadius(0.02);

//      Collections.sort(points);

      Point point = null;
      for (int i = 0; i < points.size(); i++)
      {
         Point p = points.get(i);
         //print to standard output
         StdOut.print(p);
         if (i < points.size() - 1)
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

}