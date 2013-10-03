import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Fast {
   public static void main(String[] args)
   {
      initStdDraw();

      Point[] points = readPoints(args[0]);
      Arrays.sort(points);

      Point[] slopeOrdered = new Point[points.length];

      for (int i = 0; i < points.length; i++)
      {
         Point p0 = points[i];
         p0.draw();

         System.arraycopy(points, 0, slopeOrdered, 0, points.length);
         Arrays.sort(slopeOrdered, p0.SLOPE_ORDER);

         for (int left = 1; left < slopeOrdered.length;)
         {
            List<Point> collinear = getCollinearPoints(p0, slopeOrdered, left);
            Point p1 = collinear.get(0);
            Point p2 = collinear.get(1);
            if (collinear.size() > 3 && p1.compareTo(p2) < 0)
            {
               draw(collinear);
            }

            left += collinear.size() - 1;
         }
      }
   }

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

   private static void draw(List<Point> points)
   {
      Collections.sort(points);

      for (int i = 0; i < points.size(); i++)
      {
         Point p = points.get(i);
         //print to standard output
         StdOut.print(p);
         if (i < points.size() - 1)
         {
            StdOut.print(" -> ");
         }
      }

      //draw line
      Point first = points.get(0);
      Point last = points.get(points.size() - 1);
      first.drawTo(last);

      StdOut.println();
      StdDraw.show(0);
   }

   private static void initStdDraw()
   {
      StdDraw.setXscale(0, 32768);
      StdDraw.setYscale(0, 32768);
      StdDraw.show(0);
      StdDraw.setPenRadius(0.02);
   }

}