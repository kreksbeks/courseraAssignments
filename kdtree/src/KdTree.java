public class KdTree {

   private Node root;
   private int count;
   private Point2D closest;
   private double bestDistance = Double.POSITIVE_INFINITY;

   // construct an empty set of points
   public KdTree()
   { }

   // is the set empty?
   public boolean isEmpty()
   {
      return root == null;
   }

   // number of points in the set
   public int size()
   {
      return count;
   }

   // add the point p to the set (if it is not already in the set)
   public void insert(Point2D p)
   {
      root = insertInner(root, p, false);
   }

   private Node insertInner(Node root, Point2D value, boolean vertical)
   {
      if (root == null) {
         count++;
         return new Node(value, vertical);
      }

      if (root.p.equals(value))
      {
         root.p = value;
         return root;
      }

      double cmp = vertical ? value.x() - root.p.x() : value.y() - root.p.y();

      if (cmp < 0) root.left = insertInner(root.left, value, !vertical);
      else         root.right = insertInner(root.right, value, !vertical);

      return root;
   }

   private Node get(Node root, Point2D p)
   {
      if (root == null) return null;

      if (root.p.equals(p)) return root;

      double cmp = root.isVertical ? p.x() - root.p.x() : p.y() - root.p.y();

      if (cmp < 0)  return get(root.left, p);
      else          return get(root.right, p);
   }

   // does the set contain the point p?
   public boolean contains(Point2D p)
   {
      return get(root, p) != null;
   }

   // draw all of the points to standard draw
   public void draw()
   {
      drawInner(root, 0, 0, 1, 1);
   }

   private void drawInner(Node root, double x0, double y0, double x1, double y1)
   {
      if (root == null) return;

      StdDraw.setPenColor(StdDraw.BLACK);
      StdDraw.setPenRadius(0.01);
      root.p.draw();
      StdDraw.setPenRadius();

      double x0Left, x1Left, y0Left, y1Left;
      double x0Right, x1Right, y0Right, y1Right;

      if (root.isVertical)
      {
         StdDraw.setPenColor(StdDraw.RED);
         StdDraw.line(root.p.x(), y0, root.p.x(), y1);
         x0Left = x0;
         y0Left = y0;
         x1Left = root.p.x();
         y1Left = y1;

         x0Right = root.p.x();
         y0Right = y0;
         x1Right = x1;
         y1Right = y1;
      }
      else
      {
         StdDraw.setPenColor(StdDraw.BLUE);
         StdDraw.line(x0, root.p.y(), x1, root.p.y());
         x0Left = x0;
         y0Left = y0;
         x1Left = x1;
         y1Left = root.p.y();

         x0Right = x0;
         y0Right = root.p.y();
         x1Right = x1;
         y1Right = y1;
      }

      drawInner(root.left, x0Left, y0Left, x1Left, y1Left);
      drawInner(root.right, x0Right, y0Right, x1Right, y1Right);
   }

   // all points in the set that are inside the rectangle
   public Iterable<Point2D> range(RectHV rect)
   {
      SET<Point2D> r = new SET<Point2D>();

      if (rect == null) return r;

      rangeInner(root, rect, r);

      return r;
   }

   private void rangeInner(Node root, RectHV range, SET<Point2D> within)
   {
      if (root == null) return;

      if (range.contains(root.p)) within.add(root.p);

      boolean left = root.isVertical ? range.xmin() < root.p.x() : range.ymin() < root.p.y();
      boolean right = root.isVertical ? range.xmax() >= root.p.x() : range.ymax() >= root.p.y();

      if (left)  rangeInner(root.left, range, within);
      if (right) rangeInner(root.right, range, within);
   }

   // a nearest neighbor in the set to p; null if set is empty
   public Point2D nearest(Point2D p)
   {
      if (isEmpty()) return null;

      closest = null;
      bestDistance = Double.POSITIVE_INFINITY;
      nearestInner(root, p);
      return closest;
   }

   private void nearestInner(Node current, Point2D p)
   {
      if (current == null) return;

      double dist = current.p.distanceSquaredTo(p);

      if (dist < bestDistance)
      {
         bestDistance = dist;
         closest = current.p;
      }

      double d = current.isVertical ? current.p.x() - p.x() : current.p.y() - p.y();

      if   (d > 0)  nearestInner(current.left, p);
      else          nearestInner(current.right, p);

      if (Math.abs(d * d) < bestDistance)
      {
         if (d > 0) nearestInner(current.right, p);
         else       nearestInner(current.left, p);
      }
   }

   private static class Node
   {
      Point2D p;
      Node left;
      Node right;
      boolean isVertical;

      Node(Point2D p, boolean vertical)
      {
         this.p = p;
         this.isVertical = vertical;
      }
   }
}