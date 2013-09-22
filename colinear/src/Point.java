/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {
    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrderComparator();

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that)
    {
       double yDelta = that.y - this.y;
       double xDelta = that.x - this.x;

       if (yDelta == 0 && xDelta == 0) return Double.NEGATIVE_INFINITY;
       if (xDelta == 0) return Double.POSITIVE_INFINITY;
       if (yDelta == 0) return 0;

       return yDelta / xDelta;
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
   @Override
    public int compareTo(Point that) {
        if (less(this, that)) return -1;
        if (less(that, this)) return 1;
        return 0;
    }

   private boolean less(Point p1, Point p2)
   {
      return p1.y < p2.y || (p1.y == p2.y && p1.x < p2.x);
   }

   // return string representation of this point
   @Override
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }

   private class SlopeOrderComparator implements Comparator<Point>
   {
      @Override
      public int compare(Point p1, Point p2)
      {
         if (lessTo(p1, p2)) return -1;
         if (lessTo(p2, p1)) return 1;
         return 0;
      }

      private boolean lessTo(Point p1, Point p2)
      {
         return Point.this.slopeTo(p1) < Point.this.slopeTo(p2);
      }
   }
}