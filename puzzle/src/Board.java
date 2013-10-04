import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Board
{
   private static final int GAP = 0;

   private final char[] blocks;
   private int gapPosition = -1;
   private int dimension;
   private int manhattanDistance = -1;

   // construct a board from an N-by-N array of blocks
   // (where blocks[i][j] = block in row i, column j)
   public Board(int[][] blocks)
   {
      dimension = blocks.length;
      this.blocks = new char[dimension * dimension];

      for (int i = 0; i < dimension; i++)
      {
         for (int j = 0; j < dimension; j++)
         {
            char v = (char) blocks[i][j];
            this.blocks[to1D(i, j)] = v;

            if (v == GAP) gapPosition = to1D(i, j);
         }
      }
   }

   private Board(char[] twin, int dimension, int gap)
   {
      this.blocks = twin;
      this.dimension = dimension;
      this.gapPosition = gap;
   }

   // board dimension N
   public int dimension()
   {
      return dimension;
   }

   // number of blocks out of place
   public int hamming()
   {
      int numberOfMisplacedBlocks = 0;

      for (int i = 0; i < blocks.length; i++)
      {
         if (blocks[i] == GAP) continue;
         if (blocks[i] != expectedValue(i)) numberOfMisplacedBlocks++;
      }

      return numberOfMisplacedBlocks;
   }

   // sum of Manhattan distances between blocks and goal
   public int manhattan()
   {
      if (manhattanDistance != -1) return manhattanDistance;

      manhattanDistance = 0;

      for (int i = 0; i < blocks.length; i++)
      {
         char block = blocks[i];
         manhattanDistance += manhattanDistance(block, i);
      }

      return manhattanDistance;
   }

   private int manhattanDistance(int block, int p)
   {
      if (block == GAP) return 0;

      Index current = new Index(p).invoke();
      Index expected = new Index(expectedPosition(block)).invoke();

      int i = current.y();
      int j = current.x();

      int dy = Math.abs(expected.y() - i);
      int dx = Math.abs(expected.x() - j);
      return dy + dx;
   }

   // is this board the goal board?
   public boolean isGoal()
   {
      for (int i = 0; i < blocks.length; i++)
      {
         if (blocks[i] != expectedValue(i))
         {
            return false;
         }
      }

      return true;
   }

   private int expectedValue(int i)
   {
      return i == lastIndex() ? GAP : i + 1;
   }

   private int expectedPosition(int v)
   {
      return v == GAP ? lastIndex() : v - 1;
   }

   private int lastIndex()
   {
      return dimension() * dimension() - 1;
   }

   // a board obtained by exchanging two adjacent blocks in the same row
   public Board twin()
   {
      char[] twin = Arrays.copyOf(blocks, blocks.length);

      char prev = GAP;
      for (int i = 0; i < blocks.length; i++)
      {
         if (i % dimension() == 0) prev = GAP;

         char current = twin[i];
         if (prev != GAP && current != GAP)
         {
            twin[i - 1] = current;
            twin[i] = prev;
            break;
         }
         prev = current;
      }

      return new Board(twin, dimension(), gapPosition);
   }

   // all neighboring boards
   public Iterable<Board> neighbors() {
      Set<Board> neighbours = new HashSet<Board>();

      Index gap = new Index(gapPosition).invoke();

      if (gap.leftShiftAllowed()) {
         neighbours.add(getBoard(gapPosition - 1));
      }
      if (gap.rightShiftAllowed()) {
         neighbours.add(getBoard(gapPosition + 1));
      }
      if (gap.topShiftAllowed()) {
         neighbours.add(getBoard(to1D(gap.y() - 1, gap.x())));
      }
      if (gap.bottomShiftAllowed()) {
         neighbours.add(getBoard(to1D(gap.y() + 1, gap.x())));
      }

      return neighbours;
   }

   private int to1D(int row, int column) {
      return row * dimension() + column;
   }

   private Board getBoard(int newGapPosition) {
      char[] horizontalShift = Arrays.copyOf(blocks, blocks.length);
      Board b = new Board(horizontalShift, dimension(), newGapPosition);
      horizontalShift[gapPosition] = horizontalShift[newGapPosition];
      horizontalShift[newGapPosition] = GAP;
      return b;
   }

   // does this board equal y?
   @Override
   public boolean equals(Object y)
   {
      if (y == null) return false;

      if (Board.class != y.getClass()) return false;

      Board that = (Board) y;
      return Arrays.equals(blocks, that.blocks);
   }

   // string representation of the board (in the output format specified below)
   @Override
   public String toString()
   {
      StringBuilder s = new StringBuilder(dimension() + "\n");

      for (int i = 0; i < blocks.length; i++)
      {
         int block = blocks[i];
         s.append(String.format("%3d", block));

         if ((i + 1) % dimension() == 0)
         {
            s.append("\n");
         }
      }

      return s.toString();
   }

   private class Index
   {
      private int p;
      private int i;
      private int j;

      public Index(int p)
      {
         this.p = p;
      }

      public int y()
      {
         return i;
      }

      public int x()
      {
         return j;
      }

      public Index invoke()
      {
         i = p / dimension();
         j = p - i * dimension();
         return this;
      }

      public boolean leftShiftAllowed() {
         return j > 0;
      }

      public boolean rightShiftAllowed() {
         return j < dimension() - 1;
      }

      public boolean topShiftAllowed() {
         return i > 0;
      }

      public boolean bottomShiftAllowed() {
         return i < dimension() - 1;
      }
   }
}