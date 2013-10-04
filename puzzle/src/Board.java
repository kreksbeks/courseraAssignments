import com.sun.org.apache.bcel.internal.generic.NEW;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

public class Board
{
   private static final int GAP = 0;

   private final char[] blocks;
   private int gapPosition = -1;

   // construct a board from an N-by-N array of blocks
   // (where blocks[i][j] = block in row i, column j)
   public Board(int[][] blocks)
   {
      this.blocks = new char[blocks.length * blocks.length];

      for (int i = 0; i < blocks.length; i++)
      {
         for (int j = 0; j < blocks.length; j++)
         {
            char v = (char) blocks[i][j];
            int index1D = i * dimension() + j;
            this.blocks[index1D] = v;

            if (v == GAP) gapPosition = index1D;
         }
      }
   }

   private Board(char[] twin)
   {
      blocks = twin;
   }

   // board dimension N
   public int dimension()
   {
      return blocks.length;
   }

   // number of blocks out of place
   public int hamming()
   {
      int numberOfMisplacedBlocks = 0;

      for (int i = 0; i < blocks.length; i++)
      {
         if (blocks[i] != valueForPosition(i)) numberOfMisplacedBlocks++;
      }

      return numberOfMisplacedBlocks;
   }



   // sum of Manhattan distances between blocks and goal
   public int manhattan()
   {
      int sumOfDistancesToGoal = 0;

      for (int i = 0; i < blocks.length; i++)
      {
         char block = blocks[i];
         sumOfDistancesToGoal += manhattanDistance(block, i);
      }

      return sumOfDistancesToGoal;
   }

   private int manhattanDistance(char block, int i, int j)
   {
      int valueForPosition = block == GAP ? dimension() : valueForPosition(i, j);

      return dimension() / valueForPosition + (dimension() - 1) - j;
   }

   private int manhattanDistance(char block, int p)
   {
      Index index = new Index(p).invoke();

      int i = index.y();
      int j = index.x();

      int dy = Math.abs(positionForValue(block) / dimension() - i);
      int dx = (dimension() - 1) - j;
      return dy + dx;
   }

   // is this board the goal board?
   public boolean isGoal()
   {
      for (int i = 0; i < dimension(); i++)
      {
         if (blocks[i] != valueForPosition(i))
         {
            return false;
         }
      }

      return true;
   }

   private int valueForPosition(int i, int j)
   {
      if (i == dimension() - 1 && j == dimension() - 1) return 0;

      return (i * dimension() + j) + 1;
   }

   private int valueForPosition(int i)
   {
      return (i == lastIndex()) ? GAP : i + 1;
   }

   private int positionForValue(int v)
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

      char prev = 0;
      for (int i = 0; i < dimension(); i++)
      {
         char current = twin[i];

         if (prev != GAP && current != GAP)
         {
            twin[i - 1] = current;
            twin[i] = prev;
            break;
         }

         prev = current;
      }

      return new Board(twin);
   }

   // all neighboring boards
   public Iterable<Board> neighbors()
   {
      Set<Board> neighbours = new TreeSet<Board>();
      neighbours.add(verticalShiftNeighbour());
      neighbours.add(horizontalShiftNeighbour());

      return neighbours;
   }

   private Board horizontalShiftNeighbour()
   {
      Index gapIndex = new Index(gapPosition).invoke();

      char[] horizontalShift = Arrays.copyOf(blocks, blocks.length);
      Board b = new Board(horizontalShift);

      int newGapPosition;
      if (gapIndex.x() == 0)
      {
         newGapPosition = gapPosition + 1;
      }
      else
      {
         newGapPosition = gapPosition - 1;
      }

      horizontalShift[gapPosition] = horizontalShift[newGapPosition];
      horizontalShift[gapPosition] = GAP;
      b.gapPosition++;

      return b;
   }

   private Board verticalShiftNeighbour()
   {
      return new char[0];
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
      StringBuilder s = new StringBuilder(blocks.length + "\n");

      for (int i = 0; i < blocks.length; i++)
      {
         char block = blocks[i];
         s.append(" ").append(block);

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
         j = p - i * dimension() - 1;
         return this;
      }
   }
}