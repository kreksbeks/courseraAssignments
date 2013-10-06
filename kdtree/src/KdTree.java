public class KdTree {

   private Node root;
   private int count;
   private Point2D closest;

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
      root = insertInner(root, p, true);
   }

   private Node insertInner(Node root, Point2D value, boolean vertical)
   {
      if (root == null) {
         count++;
         return new Node(value, vertical);
      }

      double cmp = vertical ? root.p.x() - value.x() : root.p.y() - value.y();

      if      (cmp > 0) root.left = insertInner(root.left, value, !vertical);
      else if (cmp < 0) root.right = insertInner(root.right, value, !vertical);
      else              root.p = value;

      return root;
   }

   private Node get(Node root, Point2D p)
   {
      if (root == null) return null;

      int cmp = root.p.compareTo(p);

      if      (cmp > 0) return get(root.left, p);
      else if (cmp > 0) return get(root.right, p);
      else              return root;
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
      rangeInner(root, rect, r);
      return r;
   }

   private void rangeInner(Node root, RectHV rect, SET<Point2D> within)
   {
      if (root == null || rect.height() == 0) return;

      if (rect.contains(root.p)) within.add(root.p);

      boolean right = root.right != null;
      boolean left = root.left != null;

      if (root.isVertical)
      {
         right = right && rect.xmax() >= root.p.x();
         left = left && rect.xmin() <= root.p.x();
      }
//      else
//      {
//         right = right && rect.ymax() >= root.p.y();
//         left = left && rect.ymin() <= root.p.y();
//      }

      if (right) rangeInner(root.right, rect, within);
      if (left) rangeInner(root.left, rect, within);
   }

   // a nearest neighbor in the set to p; null if set is empty
   public Point2D nearest(Point2D p)
   {
      if (isEmpty()) return null;

      closest = root.p;
      return nearestInner(root, p, root.p);
   }

   private Point2D nearestInner(Node current, Point2D p, Point2D closest)
   {
      if (current == null) return closest;

      double minDist = closest.distanceSquaredTo(p);
      double dist = current.p.distanceSquaredTo(p);
      if (dist < minDist)
      {
         closest = current.p;
      }

      double d = current.isVertical ? p.x() - current.p.x() : p.y() - current.p.y();

      if (d < 0)
      {
         if (current.left != null)
         {
            closest = nearestInner(current.left, p, closest);
         }
         if (current.right != null && -d < minDist)
         {
            closest = nearestInner(current.right, p, closest);
         }
      }
      else
      {
         if (current.right != null)
         {
            closest = nearestInner(current.right, p, closest);
         }
         if (current.left != null && d < minDist)
         {
            closest = nearestInner(current.left, p, closest);
         }
      }

      return closest;
   }

   private static class Node
   {
      Point2D p;
      Node left;
      Node right;
      boolean isVertical;

      public Node(Point2D p, boolean vertical)
      {
         this.p = p;
         this.isVertical = vertical;
      }
   }
}